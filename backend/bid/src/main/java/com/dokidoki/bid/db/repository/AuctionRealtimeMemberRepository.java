package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.common.annotation.RTransactional;
import com.dokidoki.bid.common.codes.RealTimeConstants;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@Slf4j
public class AuctionRealtimeMemberRepository {

    private final RedissonClient redisson;
    private final String keyPrefix = RealTimeConstants.memberPriceKey;

    private String getKey(Long auctionId) {
        StringBuilder sb = new StringBuilder();
        sb.append(keyPrefix).append(":").append(auctionId);
        return sb.toString();
    }

    public Long findById(Long auctionId, Long memberId) {
        RMap<Long, Long> map = redisson.getMap(getKey(auctionId));
        Long myBidPrice = map.get(memberId);
        if (myBidPrice == null) {
            throw new InvalidValueException("조회할 수 없는 값입니다.");
        }
        return myBidPrice;
    }

    @RTransactional
    public void save(Long auctionId, Long memberId, Long bidPrice) {
        RMap<Long, Long> map = redisson.getMap(getKey(auctionId));
        map.put(memberId, bidPrice);
    }





}
