package com.dokidoki.bid.api.service;

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

    public void bid(long auctionId, long memberId) {
        Optional<AuctionRealtime> auctionRealTime = auctionRealtimeRepository.findById(auctionId);

        // 현재 최고가 갱신
        
        // LeaderBoard 갱신

    }

    @Transactional
    public void updatePriceSize(long auctionId, int priceSize) {
        Optional<AuctionRealtime> auctionRealTimeO = auctionRealtimeRepository.findById(auctionId);
        if (auctionRealTimeO.isPresent()) {
            auctionRealTimeO.get().updatePriceSize(priceSize);
        }else {
            throw new InvalidValueException("잘못된 접근입니다. auctionId가 존재하지 않습니다.");
        }
    }



}
