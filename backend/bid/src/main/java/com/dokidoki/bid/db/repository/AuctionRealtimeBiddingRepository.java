package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.common.annotation.RTransactional;
import com.dokidoki.bid.common.codes.RealTimeConstants;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionRealtimeBiddingRepository {

    private final RedissonClient redisson;
    private final String keyPrefix = RealTimeConstants.auctionBiddingKey;

    private String getKey(Long memberId) {
        StringBuilder sb = new StringBuilder();
        sb.append(keyPrefix).append(":").append(memberId);
        return sb.toString();
    }

    public Set<Long> findById(Long memberId) {
        RSet<Long> rSet = redisson.getSet(getKey(memberId));
        Set<Long> set = rSet.readAll();
        if (set == null) {
            return new HashSet<Long>();
        }
        return set;
    }

    @RTransactional
    public void save(Long memberId, Long auctionId) {
        RSet<Long> rSet = redisson.getSet(getKey(memberId));
        rSet.add(auctionId);
    }

    @RTransactional
    public void delete(Long memberId, Long auctionId) {
        RSet<Long> rSet = redisson.getSet(getKey(memberId));
        rSet.remove(auctionId);
    }
}
