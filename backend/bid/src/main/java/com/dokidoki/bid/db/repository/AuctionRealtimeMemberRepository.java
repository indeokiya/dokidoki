package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.common.annotation.RTransactional;
import com.dokidoki.bid.common.codes.RealTimeConstants;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
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
    /**
     * RMap : auctionId 당 하나씩 배정
     * Map 에는 memberId를 key 로 , 그 memberId가 입찰한 최고가와 auctionId 에서의 식별자를 제공
     */

    private final RedissonClient redisson;
    private final String keyPrefix = RealTimeConstants.memberPriceKey;

    private String getKey(Long auctionId) {
        StringBuilder sb = new StringBuilder();
        sb.append(keyPrefix).append(":").append(auctionId);
        return sb.toString();
    }

    public Set<Map.Entry<Long, Long[]>> getAll(Long auctionId) {
        RMap<Long, Long[]> map = redisson.getMap(getKey(auctionId));
        return map.entrySet();
    }

    public Long findMyBidPriceById(Long auctionId, Long memberId) {
        RMap<Long, Long[]> map = redisson.getMap(getKey(auctionId));
        Long[] infos = map.get(memberId);
        if (infos == null) {
            throw new InvalidValueException("조회할 수 없는 값입니다.");
        }
        return infos[0];
    }

    @RTransactional
    public Long[] save(Long auctionId, Long memberId, Long bidPrice) {
        RMap<Long, Long[]> map = redisson.getMap(getKey(auctionId));
        Long[] infos = new Long[2];
        if (map.containsKey(memberId)) {
            infos = map.get(memberId);
        } else {
            infos[1] = (long) map.size();
        }
        infos[0] = bidPrice;
        map.put(memberId, infos);

        return infos;
    }
}
