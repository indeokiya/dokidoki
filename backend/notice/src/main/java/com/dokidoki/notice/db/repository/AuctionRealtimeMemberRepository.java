package com.dokidoki.notice.db.repository;

import com.dokidoki.notice.common.annotation.RTransactional;
import com.dokidoki.notice.common.codes.RealTimeConstants;
import com.dokidoki.notice.common.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;


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

    public Set<Map.Entry<Long, Integer>> getAll(Long auctionId) {
        RMap<Long, Integer> map = redisson.getMap(getKey(auctionId));
        return map.entrySet();
    }

    public int findById(Long auctionId, Long memberId) {
        RMap<Long, Integer> map = redisson.getMap(getKey(auctionId));
        Integer myBidPrice = map.get(memberId);
        if (myBidPrice == null) {
            throw new InvalidValueException("조회할 수 없는 값입니다.");
        }
        return myBidPrice;
    }

    @RTransactional
    public void save(Long auctionId, Long memberId, int bidPrice) {
        RMap<Long, Integer> map = redisson.getMap(getKey(auctionId));
        map.put(memberId, bidPrice);
    }

    @RTransactional
    public boolean deleteAll(Long auctionId) {
        RMap<Long, Integer> map = redisson.getMap(getKey(auctionId));
        return map.delete();
    }





}
