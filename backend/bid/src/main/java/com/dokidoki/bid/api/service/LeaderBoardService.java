package com.dokidoki.bid.api.service;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.dokidoki.bid.api.request.AuctionUpdatePriceSizeReq;
import com.dokidoki.bid.common.codes.LeaderBoardConstants;
import com.dokidoki.bid.common.error.exception.ErrorCode;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.db.repository.AuctionRealtimeRepository;
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
    private final RedisTemplate redisTemplate;

    // 게시글을 등록하면서 시작 가격과 경매 단위가 레디스로 넘어오는 과정이 필요

    @Transactional
    public void bid(long auctionId, AuctionBidReq req) {
        long memberId = req.getMemberId();

        Optional<AuctionRealtime> auctionRealTimeO = auctionRealtimeRepository.findById(auctionId);

        if (auctionRealTimeO.isEmpty()) {
            throw new InvalidValueException("잘못된 접근입니다. auctionId가 존재하지 않습니다.");
        }

        AuctionRealtime auctionRealtime = auctionRealTimeO.get();

        // 현재 최고가 갱신
        int newHighestPrice = auctionRealtime.updateHighestPrice();

        // LeaderBoard 갱신
        int limit = LeaderBoardConstants.limit;

        String key = "realtime"+auctionId;
        redisTemplate.opsForZSet().add(key, memberId, newHighestPrice);

        redisTemplate.opsForZSet().removeRange(key, -limit-1, -limit-1);

    }

    @Transactional
    public void updatePriceSize(long auctionId, AuctionUpdatePriceSizeReq req) {
        Optional<AuctionRealtime> auctionRealTimeO = auctionRealtimeRepository.findById(auctionId);

        // 1. 없는 auctionId면 에러 내기
        if (auctionRealTimeO.isEmpty()) {
            throw new InvalidValueException("잘못된 접근입니다. auctionId가 존재하지 않습니다.");
        }

        // 2. 해당 경매를 올린 사용자가 아니면 에러 내기



        // 3. 가격 수정하기

        auctionRealTimeO.get().updatePriceSize(req.getPriceSize());


    }
}
