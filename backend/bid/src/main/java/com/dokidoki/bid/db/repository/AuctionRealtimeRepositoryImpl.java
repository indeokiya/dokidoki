package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.common.annotation.RTransactional;
import com.dokidoki.bid.common.codes.RealTimeConstants;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuctionRealtimeRepositoryImpl implements AuctionRealtimeRepository{

    private final RedissonClient redisson;
    private String key = RealTimeConstants.key;
    private RMapCache<Long, AuctionRealtime> map;

    @Autowired
    public void setAuctionRealtimeRepositoryImpl() {
        this.key = RealTimeConstants.key;
        this.map = redisson.getMapCache(key);
        map.addListener(getExpiredListener());

    }

    @Override
    public Optional<AuctionRealtime> findById(long auctionId) {
        AuctionRealtime auctionRealtime = map.get(auctionId);
        if (auctionRealtime == null) {
            return Optional.empty();
        } else {
            return Optional.of(auctionRealtime);
        }
    }

    @Override
    @RTransactional
    public void save(AuctionRealtime auctionRealtime) {
        map.put(auctionRealtime.getAuctionId(), auctionRealtime);
    }

    @Override
    @RTransactional
    public void save(AuctionRealtime auctionRealtime, long ttl, TimeUnit timeUnit) {
        map.put(auctionRealtime.getAuctionId(), auctionRealtime, ttl, timeUnit);
    }

    @Override
    @RTransactional
    public boolean deleteAll() {
        return map.delete();
    }


    private EntryExpiredListener<Long, AuctionRealtime> getExpiredListener() {
        EntryExpiredListener<Long, AuctionRealtime> expiredListener = new EntryExpiredListener<Long, AuctionRealtime>() {
            @Override
            public void onExpired(EntryEvent<Long, AuctionRealtime> event) {
                Long key = event.getKey();
                AuctionRealtime value = event.getValue();
                log.info("auctionInfo expired. key: {}, value: {}", key, value);

                // 1. TODO - 기간이 끝나면 Kafka 에 메시지 써서 auction 서버에 알리기

                // 2. 리더보드 정보 지우기
            }
        };

        return expiredListener;
    }

}
