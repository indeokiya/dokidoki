package com.dokidoki.bid.api.service;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.dokidoki.bid.api.request.AuctionUpdatePriceSizeReq;
import com.dokidoki.bid.api.response.AuctionInitialInfoResp;
import com.dokidoki.bid.api.response.LeaderBoardMemberInfo;
import com.dokidoki.bid.api.response.LeaderBoardMemberResp;
import com.dokidoki.bid.common.codes.LeaderBoardConstants;
import com.dokidoki.bid.common.error.exception.BusinessException;
import com.dokidoki.bid.common.error.exception.ErrorCode;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import com.dokidoki.bid.db.entity.AuctionIngEntity;
//import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.db.repository.AuctionIngRepository;
//import com.dokidoki.bid.db.repository.AuctionRealtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BiddingService {

//    private final AuctionRealtimeRepository auctionRealtimeRepository;
    private final AuctionIngRepository auctionIngRepository;
//    private final RedisTemplate redisTemplate;

    // TODO - 게시글을 등록하면서 시작 가격과 경매 단위가 레디스로 넘어오는 과정이 필요
    //  TTL 설정도 해줘야.

    public AuctionInitialInfoResp getInitialInfo(long auctionId) {
        
//        Optional<AuctionRealtime> auctionRealtimeO = auctionRealtimeRepository.findById(auctionId);
        
        // 1. 경매 정보가 없는 경우, 에러 발생시키기
//        if (auctionRealtimeO.isEmpty()) {
//            throw new InvalidValueException("잘못된 접근입니다. auctionId가 존재하지 않습니다.");
//        }

        AuctionInitialInfoResp resp = AuctionInitialInfoResp.builder()
//                .highestPrice(auctionRealtimeO.get().getHighestPrice())
//                .priceSize(auctionRealtimeO.get().getPriceSize())
                .leaderBoard(getInitialLeaderBoard(auctionId))
                .build();

        return resp;
    }


    /**
     * 경매에 입찰하는 메서드. 컨트롤러에서 접근하는 메서드.
     * @param auctionId 경매 ID
     * @param req client 측에서 넘어온 요청 정보
     */
    public void bid(long auctionId, AuctionBidReq req) {
        // TODO - 분산 락 처리 과정 필요

        // TODO - 나중에 토큰에서 받아오는 걸로 수정하기
        long memberId = req.getMemberId();


        // 1. 경매 정보가 없는 경우 - 에러 발생시키기
//        Optional<AuctionRealtime> auctionRealtimeO = auctionRealtimeRepository.findById(auctionId);

//        if (auctionRealtimeO.isEmpty()) {
//            throw new InvalidValueException("잘못된 접근입니다. auctionId가 존재하지 않습니다.");
//        }

        // 2. 실시간 DB 정보와 client 측 정보가 일치하는지 확인하기 (경매 단위, 현재 가격)

        // 2-1. 경매 단위가 일치하지 않을 경우
//        if (auctionRealtimeO.get().getPriceSize() != req.getCurrentPriceSize()) {
//            throw new BusinessException("경매 단위가 갱신되었습니다. 다시 시도해주세요", ErrorCode.DIFFERENT_PRICE_SIZE);
//        }

        // 2-2. 현재 가격이 일치하지 않을 경우
//        if (auctionRealtimeO.get().getHighestPrice() != req.getCurrentHighestPrice()) {
//            throw new BusinessException("현재 가격이 갱신되었습니다. 다시 시도해주세요", ErrorCode.DIFFERENT_HIGHEST_PRICE);
//        }

        // 3. 실시간 최고가, 리더보드 갱신하기
//        AuctionRealtime auctionRealtime = auctionRealtimeO.get();
        String key = getKey(auctionId);

//        LeaderBoardMemberResp resp = updateLeaderBoardAndHighestPrice(auctionRealtime, key, req);

        // TODO - 4. Kafka 에 갱신된 최고 입찰 정보 (resp) 보내기
        //  MySQL 도 구독해놓고, 최고가 정보를 받아야 함

    }

    /**
     * 리더보드와 실시간 최고가를 갱신하는 메서드
     * @param auctionRealtime redis 에 저장되어 있는 실시간 경매 정보
     * @param key redis 에 리더보드가 저장된 키
     * @param req client 측에서 넘어온 요청 정보
     * @return newHighestPrice
     */
//    @Transactional
//    public LeaderBoardMemberResp updateLeaderBoardAndHighestPrice(AuctionRealtime auctionRealtime, String key, AuctionBidReq req) {
//
//        // 3-1. 실시간 최고가 갱신
//        int newHighestPrice = auctionRealtime.updateHighestPrice();
//
//        auctionRealtimeRepository.save(auctionRealtime);
//
//        // 3-2. 리더보드 갱신
//        int limit = LeaderBoardConstants.limit;
//
//        LeaderBoardMemberInfo memberInfo = LeaderBoardMemberInfo.of(req);
//
//        redisTemplate.opsForZSet().add(key, memberInfo, newHighestPrice);
//        redisTemplate.opsForZSet().removeRange(key, -limit -1, -limit -1);
//
//        LeaderBoardMemberResp resp = LeaderBoardMemberResp.of(memberInfo, newHighestPrice);
//
//        return resp;
//    }

    /**
     * auctionId로 Redis 에 leaderboard 를 저장할 키를 생성하는 메서드
     * @param auctionId 경매 ID
     * @return Redis 에 leaderboard 를 저장할 키
     */
    public String getKey(long auctionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("realtime:").append(auctionId);
        return sb.toString();
    }

    /**
     * Redis 의 SortedSet 에 저장된 leaderboard 정보를 바탕으로
     * client 에게 보내줄 리더보드 정보를 가공하는 메서드. 컨트롤러에서 접근하는 메서드.
     * @param auctionId 경매 ID
     * @return client 에게 보내줄 리더보드 정보
     */
    public List<LeaderBoardMemberResp> getInitialLeaderBoard(long auctionId) {
        List<LeaderBoardMemberResp> list = new ArrayList<>();

        String key = getKey(auctionId);

//        Set<Object> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, -1);

//        for (Object o : set) {
//            DefaultTypedTuple tuple = (DefaultTypedTuple) o;
//            int bidPrice = tuple.getScore().intValue();
//            LeaderBoardMemberInfo memberInfo = (LeaderBoardMemberInfo) tuple.getValue();
//            LeaderBoardMemberResp resp = LeaderBoardMemberResp.of(memberInfo, bidPrice);
//            list.add(resp);
//        }
        return list;
    }

    /**
     * 경매 단위를 수정하는 메서드. 컨트롤러에서 접근하는 메서드
     * @param auctionId 경매 ID
     * @param req client 측에서 넘어온 요청 정보
     */
    @Transactional
    public void updatePriceSize(long auctionId, AuctionUpdatePriceSizeReq req) {
        // TODO - 분산 락 처리 과정 필요

//        Optional<AuctionRealtime> auctionRealTimeO = auctionRealtimeRepository.findById(auctionId);

        // 1. 없는 auctionId면 에러 내기
//        if (auctionRealTimeO.isEmpty()) {
//            throw new InvalidValueException("잘못된 접근입니다. auctionId가 존재하지 않습니다.");
//        }

        // 2. 해당 경매를 올린 사용자가 아니면 에러 내기
        Optional<AuctionIngEntity> auctionIngO = auctionIngRepository.findBySellerIdAndId(req.getMemberId(), auctionId, AuctionIngEntity.class);

        if (auctionIngO.isEmpty()) {
            throw new BusinessException("권한이 없습니다.", ErrorCode.BUSINESS_EXCEPTION_ERROR);
        }

        // 3. 가격 수정하기

//        auctionRealTimeO.get().updatePriceSize(req.getPriceSize());

        // 수정사항 저장하기 (JPA 는 dirty check 가 되는데, redis 는 안되는 듯)
//        auctionRealtimeRepository.save(auctionRealTimeO.get());
        
        // TODO - 4. Kafka 에 수정된 단위 가격 (req.getPriceSize()) 보내기
        //  -> MySQL 도 구독해놔야
        

    }

}
