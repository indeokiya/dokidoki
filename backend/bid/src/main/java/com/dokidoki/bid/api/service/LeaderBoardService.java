package com.dokidoki.bid.api.service;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.dokidoki.bid.api.request.AuctionUpdatePriceSizeReq;
import com.dokidoki.bid.api.response.LeaderBoardMemberInfo;
import com.dokidoki.bid.common.codes.LeaderBoardConstants;
import com.dokidoki.bid.common.error.exception.BusinessException;
import com.dokidoki.bid.common.error.exception.ErrorCode;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import com.dokidoki.bid.db.entity.AuctionIngEntity;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.db.repository.AuctionIngRepository;
import com.dokidoki.bid.db.repository.AuctionRealtimeRepository;
import com.dokidoki.bid.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeaderBoardService {

    private final AuctionRealtimeRepository auctionRealtimeRepository;
    private final AuctionIngRepository auctionIngRepository;
    private final RedisTemplate redisTemplate;

    // TODO - 게시글을 등록하면서 시작 가격과 경매 단위가 레디스로 넘어오는 과정이 필요
    //  TTL 설정도 해줘야.

    @Transactional
    public void bid(long auctionId, AuctionBidReq req) {
        // TODO - 분산 락 처리 과정 필요
        
        // TODO - 나중에 토큰에서 받아오는 걸로 수정하기
        long memberId = req.getMemberId();

        // 1. 경매 정보가 없는 경우 - 에러 발생시키기
        Optional<AuctionRealtime> auctionRealtimeO = auctionRealtimeRepository.findById(auctionId);

        if (auctionRealtimeO.isEmpty()) {
            throw new InvalidValueException("잘못된 접근입니다. auctionId가 존재하지 않습니다.");
        }
        
        // 2. 실시간 DB 정보와 client 측 정보가 일치하는지 확인하기 (경매 단위, 현재 가격)
        
        // 2-1. 경매 단위가 일치하지 않을 경우
        if (auctionRealtimeO.get().getPriceSize() != req.getCurrentPriceSize()) {
            throw new BusinessException("경매 단위가 갱신되었습니다. 다시 시도해주세요", ErrorCode.DIFFERENT_PRICE_SIZE);

        }

        // 2-2. 현재 가격이 일치하지 않을 경우
        if (auctionRealtimeO.get().getHighestPrice() != req.getCurrentHighestPrice()) {
            throw new BusinessException("현재 가격이 갱신되었습니다. 다시 시도해주세요", ErrorCode.DIFFERENT_HIGHEST_PRICE);
        }

        // 3. 실시간 최고가, 리더보드 갱신하기
        AuctionRealtime auctionRealtime = auctionRealtimeO.get();

        // 3-1. 실시간 최고가 갱신
        int newHighestPrice = auctionRealtime.updateHighestPrice();

        auctionRealtimeRepository.save(auctionRealtime);

        // 3-2. 리더보드 갱신
        int limit = LeaderBoardConstants.limit;

        String key = getKey(auctionId);

        LeaderBoardMemberInfo memberInfo = LeaderBoardMemberInfo.of(req);

        redisTemplate.opsForZSet().add(key, memberInfo, newHighestPrice);

        redisTemplate.opsForZSet().removeRange(key, -limit -1, -limit -1);
        
        // TODO - 4. MySQL DB도 수정하기


    }

    public String getKey(long auctionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("realtime:").append(auctionId);
        return sb.toString();
    }

    @Transactional
    public void updatePriceSize(long auctionId, AuctionUpdatePriceSizeReq req) {
        // TODO - 분산 락 처리 과정 필요
        
        Optional<AuctionRealtime> auctionRealTimeO = auctionRealtimeRepository.findById(auctionId);

        // 1. 없는 auctionId면 에러 내기
        if (auctionRealTimeO.isEmpty()) {
            throw new InvalidValueException("잘못된 접근입니다. auctionId가 존재하지 않습니다.");
        }

        // 2. 해당 경매를 올린 사용자가 아니면 에러 내기
        Optional<AuctionIngEntity> auctionIngO = auctionIngRepository.findBySellerIdAndId(req.getMemberId(), auctionId, AuctionIngEntity.class);

        if (auctionIngO.isEmpty()) {
            throw new BusinessException("권한이 없습니다.", ErrorCode.BUSINESS_EXCEPTION_ERROR);
        }


        // 3. 가격 수정하기

        auctionRealTimeO.get().updatePriceSize(req.getPriceSize());

        // 수정사항 저장하기 (JPA 는 dirty check 가 되는데, redis 는 안되는 듯)
        auctionRealtimeRepository.save(auctionRealTimeO.get());

    }
}
