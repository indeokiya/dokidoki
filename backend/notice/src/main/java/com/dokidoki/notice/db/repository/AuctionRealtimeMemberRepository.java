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
    public Long findMyBidNumById(Long auctionId, Long memberId) {
        RMap<Long, Long[]> map = redisson.getMap(getKey(auctionId));
        Long[] infos = map.get(memberId);
        if (infos == null) {
            throw new InvalidValueException("조회할 수 없는 값입니다.");
        }
        return infos[1];
    }

    @RTransactional
    public void save(Long auctionId, Long memberId, Long bidPrice) {
        RMap<Long, Long[]> map = redisson.getMap(getKey(auctionId));
        Long[] infos = new Long[2];
        if (map.containsKey(memberId)) {
            infos = map.get(memberId);
        } else {
            infos[1] = (long) map.size();
        }
        infos[0] = bidPrice;
        map.put(memberId, infos);
    }

    @RTransactional
    public boolean deleteAll(Long auctionId) {
        RMap<Long, Long[]> map = redisson.getMap(getKey(auctionId));
        return map.delete();
    }





}
