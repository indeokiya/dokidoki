package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.api.response.RealtimeInterestInfo;
import com.dokidoki.bid.common.codes.AnalyzeConstants;
import com.dokidoki.bid.db.entity.enumtype.InterestType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyzeRealtimeInterestRepository {

    private final RedissonClient redisson;
    private final String keyPrefix = AnalyzeConstants.key;

    private String getKey(Long auctionId, InterestType type) {
        StringBuilder sb = new StringBuilder();
        sb.append(keyPrefix).append(":").append(auctionId).append(":").append(type);
        return sb.toString();
    }

    private String getPattern(InterestType type) {
        StringBuilder sb = new StringBuilder();
        sb.append(keyPrefix).append(":*:").append(type);
        return sb.toString();
    }

    public void save(Long auctionId, Long cnt, InterestType type, Long ttl, TimeUnit timeUnit) {
        RBucket<Long> bucket = redisson.getBucket(getKey(auctionId, type));
        bucket.set(cnt, ttl, timeUnit);
    }

    public List<Long[]> findAllByType(InterestType type) {
        Iterable<String> keysByPattern = redisson.getKeys().getKeysByPattern(getPattern(type));
        List<Long[]> resList = new ArrayList<>();
        for (String key: keysByPattern) {
            log.info("조회할 key: {}", key);
            String[] splitKey = key.split(":");
            Long auctionId = Long.parseLong(splitKey[1]);
            Long cnt = (Long) redisson.getBucket(key).get();
            Long[] resArr = new Long[2];
            resArr[0] = auctionId;
            resArr[1] = cnt;
            resList.add(resArr);
        }
        return resList;
    }
}
