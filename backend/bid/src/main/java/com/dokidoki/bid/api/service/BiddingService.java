package com.dokidoki.bid.api.service;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.dokidoki.bid.api.request.AuctionUpdatePriceSizeReq;
import com.dokidoki.bid.api.response.AuctionInitialInfoResp;
import com.dokidoki.bid.api.response.LeaderBoardMemberInfo;
import com.dokidoki.bid.api.response.LeaderBoardMemberResp;
import com.dokidoki.bid.common.annotation.RTransactional;
import com.dokidoki.bid.common.annotation.RealTimeLock;
import com.dokidoki.bid.common.error.exception.BusinessException;
import com.dokidoki.bid.common.error.exception.ErrorCode;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.db.repository.AuctionRealtimeLeaderBoardRepository;
import com.dokidoki.bid.db.repository.AuctionRealtimeMemberRepository;
import com.dokidoki.bid.db.repository.AuctionRealtimeRepository;
import com.dokidoki.bid.kafka.dto.KafkaAuctionRegisterDTO;
import com.dokidoki.bid.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.bid.kafka.dto.KafkaBidDTO;
import com.dokidoki.bid.kafka.service.KafkaBidProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BiddingService {

    private final AuctionRealtimeRepository auctionRealtimeRepository;
    private final AuctionRealtimeLeaderBoardRepository auctionRealtimeLeaderBoardRepository;
    private final AuctionRealtimeMemberRepository auctionRealtimeMemberRepository;
    private final KafkaBidProducer producer;

    /**
     * 게시글 등록 시 Redis 에 실시간 정보를 저장하는 메서드.
     * Kafka 를 통해 받아옴
     * @param dto
     */
    public void registerAuctionInfo(KafkaAuctionRegisterDTO dto) {
        AuctionRealtime auctionRealtime = AuctionRealtime.from(dto);
        auctionRealtimeRepository.save(auctionRealtime, dto.getTtl(), TimeUnit.MINUTES);
        // 경매 실패 알림을 위한 경매 id - member id set 이 필요함
    }

    /**
     * 경매 초기 정보 실시간 정보를 가져오는 메서드. 컨트롤러에서 접근하는 메서드
     * 실시간 정보가 없을 때에는 리더보드 정보만을 제공하고,
     * 실시간 정보가 있을 때에는 highestPrice 와 priceSize 도 함께 보내준다.
     * @param auctionId
     * @return
     */
    public AuctionInitialInfoResp getInitialInfo(long auctionId) {

        // 1. 초기 리더보드 정보 가져오기
        List<LeaderBoardMemberResp> initialLeaderBoard = getInitialLeaderBoard(auctionId);

        Optional<AuctionRealtime> auctionRealtimeO = auctionRealtimeRepository.findById(auctionId);
        
        // 2. 경매 정보가 없는 경우, 리더보드 정보만 보내기
        if (auctionRealtimeO.isEmpty()) {
            AuctionInitialInfoResp resp = AuctionInitialInfoResp.builder()
                    .leaderBoard(initialLeaderBoard)
                    .build();
            return resp;
        }
        
        // 3. 경매 정보가 있는 경우, 현재 최고가와 경매 단위도 같이 보내기
        AuctionInitialInfoResp resp = AuctionInitialInfoResp.builder()
                .highestPrice(auctionRealtimeO.get().getHighestPrice())
                .priceSize(auctionRealtimeO.get().getPriceSize())
                .leaderBoard(initialLeaderBoard)
                .build();
        return resp;
    }


    /**
     * 경매에 입찰하는 메서드. 컨트롤러에서 접근하는 메서드.
     * @param auctionId 경매 ID
     * @param req client 측에서 넘어온 요청 정보
     * @param memberId 접근하는 사용자의 ID
     */
    @RealTimeLock
    public void bid(long auctionId, AuctionBidReq req, long memberId) throws InterruptedException {
        log.info("req: {}", req);

        // 1. 경매 정보가 없는 경우 - 에러 발생시키기 (종료된 경매)
        Optional<AuctionRealtime> auctionRealtimeO = auctionRealtimeRepository.findById(auctionId);

        if (auctionRealtimeO.isEmpty()) {
            throw new BusinessException("종료된 경매입니다. auctionId가 존재하지 않습니다.", ErrorCode.AUCTION_ALREADY_ENDED);
        }

        log.info("auctionRealtime: {}", auctionRealtimeO.get());
        // 2. 실시간 DB 정보와 client 측 정보가 일치하는지 확인하기 (경매 단위, 현재 가격)

        // 2-1. 경매 단위가 일치하지 않을 경우
        if (auctionRealtimeO.get().getPriceSize() != req.getCurrentPriceSize()) {
            throw new BusinessException("경매 단위가 갱신되었습니다. 다시 시도해주세요", ErrorCode.DIFFERENT_PRICE_SIZE);
        }

        // 2-2. 현재 가격이 일치하지 않을 경우
        if (auctionRealtimeO.get().getHighestPrice() != req.getCurrentHighestPrice()) {
            throw new BusinessException("현재 가격이 갱신되었습니다. 다시 시도해주세요", ErrorCode.DIFFERENT_HIGHEST_PRICE);
        }

        long beforeWinnerId = -1;
        
        // 3. 입찰 강탈 여부를 확인하기 위해, 이전 최고값 입찰자 구해놓기
        Optional<LeaderBoardMemberInfo> winnerO = auctionRealtimeLeaderBoardRepository.getWinner(auctionId);
        if (winnerO.isPresent()) {
            beforeWinnerId = winnerO.get().getMemberId();
        }

        // 4. 실시간 최고가, 리더보드 갱신하기
        AuctionRealtime auctionRealtime = auctionRealtimeO.get();

        LeaderBoardMemberResp resp = updateLeaderBoardAndHighestPrice(auctionRealtime, req, memberId, auctionId);

        // 5. Kafka 에 갱신된 최고 입찰 정보 (DTO) 보내기
        KafkaBidDTO kafkaBidDTO = KafkaBidDTO.of(auctionRealtime, req, resp, memberId, beforeWinnerId);
        log.info("sending KafkaBidDTO: {}", kafkaBidDTO);
        producer.sendBid(kafkaBidDTO);
    }

    /**
     * 리더보드와 실시간 최고가를 갱신하는 메서드
     * @param auctionRealtime redis 에 저장되어 있는 실시간 경매 정보
     * @param req client 측에서 넘어온 요청 정보
     * @param memberId 접근하는 사용자의 ID
     * @return newHighestPrice
     */
    @RTransactional
    public LeaderBoardMemberResp updateLeaderBoardAndHighestPrice(AuctionRealtime auctionRealtime, AuctionBidReq req, long memberId, long auctionId) {

        // 3-1. 실시간 최고가 갱신
        int newHighestPrice = auctionRealtime.updateHighestPrice();

        auctionRealtimeRepository.save(auctionRealtime);
        
        // 3-2. 유저별 입찰 최고가 정보 갱신하기
        auctionRealtimeMemberRepository.save(auctionId, memberId, newHighestPrice);

        // 3-3. 리더보드 갱신
        // 받은 request 와 memberId로 DB에 저장되는 리더보드 정보 갱신
        LeaderBoardMemberInfo memberInfo = LeaderBoardMemberInfo.of(req, memberId);
        auctionRealtimeLeaderBoardRepository.save(newHighestPrice, memberInfo, auctionId);
        // limit 을 넘어가는 리더보드 정보는 지우기
        auctionRealtimeLeaderBoardRepository.removeOutOfRange(auctionId);
        
        // 프론트에 보내줄 리더보드 갱신 정보 가공하기
        LeaderBoardMemberResp resp = LeaderBoardMemberResp.of(memberInfo, newHighestPrice);
        
        return resp;
    }

    /**
     * Redis 의 SortedSet 에 저장된 leaderboard 정보를 바탕으로
     * client 에게 보내줄 리더보드 정보를 가공하는 메서드.
     * @param auctionId 경매 ID
     * @return client 에게 보내줄 리더보드 정보
     */
    public List<LeaderBoardMemberResp> getInitialLeaderBoard(long auctionId) {
        List<LeaderBoardMemberResp> list = new ArrayList<>();

        Collection<ScoredEntry<LeaderBoardMemberInfo>> leaderBoardMemberInfos = auctionRealtimeLeaderBoardRepository.getAll(auctionId);

        for (ScoredEntry<LeaderBoardMemberInfo> info: leaderBoardMemberInfos) {
            int bidPrice = info.getScore().intValue();
            LeaderBoardMemberResp resp = LeaderBoardMemberResp.of(info.getValue(), bidPrice);
            list.add(resp);
        }
        log.info("initialLeaderBoard: {}", list);

        return list;
    }

    /**
     * 경매 단위를 수정하는 메서드. Kafka 의 Consumer 가 사용
     * @param dto KafkaAuctionUpdateDTO
     */
    @RealTimeLock
    public void updatePriceSize(KafkaAuctionUpdateDTO dto) {
        log.info("KafkaAuctionUpdateDTO: {}", dto);
        Optional<AuctionRealtime> auctionRealTimeO = auctionRealtimeRepository.findById(dto.getAuctionId());

        // 1. 없는 auctionId면 에러 내기
        if (auctionRealTimeO.isEmpty()) {
            throw new InvalidValueException("잘못된 접근입니다. auctionId가 존재하지 않습니다.");
        }
        log.info("auctionRealTime: {}", auctionRealTimeO.get());

        // 2. 가격 수정하기
        auctionRealTimeO.get().updatePriceSize(dto.getPriceSize());

        auctionRealtimeRepository.save(auctionRealTimeO.get());

        KafkaAuctionUpdateDTO kafkaAuctionUpdateDTO = KafkaAuctionUpdateDTO.of(auctionRealTimeO.get());
        log.info("sending kafkaAuctionUpdateDTO: {}", kafkaAuctionUpdateDTO);

    }

}
