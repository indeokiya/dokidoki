package com.dokidoki.notice.db.repository;

import com.dokidoki.notice.api.service.NoticeService;
import com.dokidoki.notice.common.annotation.RTransactional;
import com.dokidoki.notice.common.codes.RealTimeConstants;
import com.dokidoki.notice.db.entity.AuctionRealtime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryEvent;
import org.redisson.api.map.event.EntryExpiredListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuctionRealtimeRepositoryImpl implements AuctionRealtimeRepository {

    private NoticeService alertService;
    private String key = RealTimeConstants.mapKey;
    private RMapCache<Long, AuctionRealtime> map;

    @Autowired
    public void setAuctionRealtimeRepositoryImpl(RedissonClient redisson, NoticeService alertService) {
        this.key = RealTimeConstants.mapKey;
        this.alertService = alertService;
        this.map = redisson.getMapCache(key);

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



}
