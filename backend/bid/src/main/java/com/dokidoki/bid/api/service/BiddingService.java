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
import com.dokidoki.bid.db.repository.AuctionRealtimeBiddingRepository;
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

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BiddingService {

    private final AuctionRealtimeRepository auctionRealtimeRepository;
    private final AuctionRealtimeLeaderBoardRepository auctionRealtimeLeaderBoardRepository;
    private final AuctionRealtimeMemberRepository auctionRealtimeMemberRepository;
    private final AuctionRealtimeBiddingRepository auctionRealtimeBiddingRepository;
    private final KafkaBidProducer producer;

    /**
     * 현재 입찰중인 경매 정보 가져오기. Controller 에서 접근하는 메서드.
     * @param memberId
     * @return
     */
    public Set<Long> auctionBiddingList(Long memberId) {
        return auctionRealtimeBiddingRepository.findById(memberId);
    }

    /**
     * 게시글 등록 시 Redis 에 실시간 정보를 저장하는 메서드.
     * Kafka 를 통해 받아옴
     * @param dto
     */
    public void registerAuctionInfo(KafkaAuctionRegisterDTO dto) {
        log.info("redis 서버에 새 경매 등록. kafkaDTO: {}", dto);
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
    public AuctionInitialInfoResp getInitialInfo(Long auctionId) {
        log.info("경매 상세 페이지에서 상세 정보 조회 요청. auctionId: {}", auctionId);

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
     * 경매 조기 종료 메서드. Controller 에서 접근하는 메서드.
     * @param auctionId
     * @param memberId
     */
    @RealTimeLock
    public void end(Long auctionId, Long memberId) {
        Optional<AuctionRealtime> auctionRealtimeO = auctionRealtimeRepository.findById(auctionId);
        
        // 1. auctionId에 해당하는 경매가 없는 경우
        if (auctionRealtimeO.isEmpty()) {
            throw new InvalidValueException("존재하지 않는 경매입니다. auctionId가 존재하지 않습니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
        
        // 2. 판매자가 아닌데 경매를 끝내려 한 경우
        if (auctionRealtimeO.get().getSellerId() != memberId) {
            throw new BusinessException("권한이 없는 사용자입니다. 판매자가 아닙니다.", ErrorCode.IS_NOT_SELLER);
        }
        
        // 3. 경매 종료시키기
        auctionRealtimeRepository.delete(auctionId);

    }

    /**
     * 경매에 입찰하는 메서드. 컨트롤러에서 접근하는 메서드.
     * @param auctionId 경매 ID
     * @param req client 측에서 넘어온 요청 정보
     * @param memberId 접근하는 사용자의 ID
     */
    @RealTimeLock
    public void bid(Long auctionId, AuctionBidReq req, Long memberId) throws InterruptedException {
        log.info("입찰 req: {}", req);

        Optional<AuctionRealtime> auctionRealtimeO = auctionRealtimeRepository.findById(auctionId);

        // 1. auctionId가 존재하지 않는 경우 (잘못된 접근)
        if (auctionRealtimeO.isEmpty()) {
            throw new InvalidValueException("존재하지 않는 경매입니다.", ErrorCode.INVALID_INPUT_VALUE);
        }

        // 2. 경매 정보가 없는 경우 - 에러 발생시키기 (종료된 경매)
        if (auctionRealtimeRepository.isExpired(auctionId)) {
            throw new BusinessException("종료된 경매입니다. auctionId가 존재하지 않습니다.", ErrorCode.AUCTION_ALREADY_ENDED);
        }
        
        // 3. 판매자는 본인이 올린 경매에 참여 불가
        if (memberId == auctionRealtimeO.get().getSellerId()) {
            throw new BusinessException("판매자는 입찰할 수 없습니다.", ErrorCode.SELLER_CANNOT_BID);
        }

        log.info("auctionRealtime: {}", auctionRealtimeO.get());
        // 4. 실시간 DB 정보와 client 측 정보가 일치하는지 확인하기 (경매 단위, 현재 가격)

        // 4-1. 경매 단위가 일치하지 않을 경우
        if (auctionRealtimeO.get().getPriceSize() != req.getCurrentPriceSize()) {
            throw new BusinessException("경매 단위가 갱신되었습니다. 다시 시도해주세요", ErrorCode.DIFFERENT_PRICE_SIZE);
        }

        // 4-2. 현재 가격이 일치하지 않을 경우
        if (auctionRealtimeO.get().getHighestPrice() != req.getCurrentHighestPrice()) {
            throw new BusinessException("현재 가격이 갱신되었습니다. 다시 시도해주세요", ErrorCode.DIFFERENT_HIGHEST_PRICE);
        }

        long beforeWinnerId = -1;
        
        // 5. 입찰 강탈 여부를 확인하기 위해, 이전 최고값 입찰자 구해놓기
        Optional<LeaderBoardMemberInfo> winnerO = auctionRealtimeLeaderBoardRepository.getWinner(auctionId);
        if (winnerO.isPresent()) {
            beforeWinnerId = winnerO.get().getMemberId();
        }

        // 6. 실시간 최고가, 리더보드 갱신하기
        AuctionRealtime auctionRealtime = auctionRealtimeO.get();

        LeaderBoardMemberResp resp = updateLeaderBoardAndHighestPrice(auctionRealtime, req, memberId, auctionId);

        // 7. Kafka 에 갱신된 최고 입찰 정보 (DTO) 보내기
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
    public LeaderBoardMemberResp updateLeaderBoardAndHighestPrice(AuctionRealtime auctionRealtime, AuctionBidReq req, Long memberId, Long auctionId) {

        // 6-1. 실시간 최고가 갱신
        Long newHighestPrice = auctionRealtime.updateHighestPrice();

        auctionRealtimeRepository.save(auctionRealtime);
        
        // 6-2. 유저별 입찰 최고가 정보 갱신하기
        auctionRealtimeMemberRepository.save(auctionId, memberId, newHighestPrice);

        // 6-3. 입찰중인 리스트에 추가
        auctionRealtimeBiddingRepository.save(memberId, auctionId);

        // 6-4. 리더보드 갱신
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
    public List<LeaderBoardMemberResp> getInitialLeaderBoard(Long auctionId) {
        List<LeaderBoardMemberResp> list = new ArrayList<>();

        Collection<ScoredEntry<LeaderBoardMemberInfo>> leaderBoardMemberInfos = auctionRealtimeLeaderBoardRepository.getAll(auctionId);

        for (ScoredEntry<LeaderBoardMemberInfo> info: leaderBoardMemberInfos) {
            Long bidPrice = info.getScore().longValue();
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
