package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.api.service.AuctionEndService;
import com.dokidoki.bid.common.annotation.RTransactional;
import com.dokidoki.bid.common.codes.RealTimeConstants;
import com.dokidoki.bid.common.error.exception.InvalidValueException;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuctionRealtimeRepositoryImpl implements AuctionRealtimeRepository {

    private String keyPrefix = RealTimeConstants.mapKey;
    private String expireKeyPrefix = RealTimeConstants.expireKey;
    private RedissonClient redisson;
    private RMap<Long, AuctionRealtime> map;
    private AuctionEndService auctionEndService;

    @Autowired
    public void setAuctionRealtimeRepositoryImpl(RedissonClient redisson, AuctionEndService auctionEndService) {
        this.redisson = redisson;
        this.keyPrefix = RealTimeConstants.mapKey;
        this.map = redisson.getMap(keyPrefix);
        this.auctionEndService = auctionEndService;
    }

    private String getExpireKey(Long auctionId) {
        StringBuilder sb = new StringBuilder();
        sb.append(expireKeyPrefix).append(":").append(auctionId);
        return sb.toString();
    }

    @Override
    public Optional<AuctionRealtime> findById(Long auctionId) {
        AuctionRealtime auctionRealtime = map.get(auctionId);
        if (auctionRealtime == null) {
            return Optional.empty();
        } else {
            return Optional.of(auctionRealtime);
        }
    }

    @Override
    public boolean isExpired(Long auctionId) {
        RBucket bucket = redisson.getBucket(getExpireKey(auctionId));
        if (bucket.get() == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void delete(Long auctionId) {
        RBucket bucket = redisson.getBucket(getExpireKey(auctionId));
        bucket.delete();
        AuctionRealtime auctionRealtime = findById(auctionId).get();
        auctionEndService.auctionEnd(auctionRealtime, getExpireKey(auctionId));
    }

    @Override
    @RTransactional
    public void save(AuctionRealtime auctionRealtime) {
        map.put(auctionRealtime.getAuctionId(), auctionRealtime);
    }

    @Override
    @RTransactional
    public void save(AuctionRealtime auctionRealtime, Long ttl, TimeUnit timeUnit) {
        Long auctionId = auctionRealtime.getAuctionId();
        RBucket<Long> bucket = redisson.getBucket(getExpireKey(auctionId));
        int listenerId = bucket.addListener(getExpiredObjectListener());
        bucket.set(auctionId, ttl, timeUnit);
        auctionRealtime.setListenerId(listenerId);

        map.put(auctionRealtime.getAuctionId(), auctionRealtime);
    }

    @Override
    @RTransactional
    public boolean deleteAll() {
        return map.delete();
    }

    /**
     * Bucket 으로 저장해둔 key 가 expire 되었는지를 확인하는 listener
     * @return
     */
    private ExpiredObjectListener getExpiredObjectListener() {
        ExpiredObjectListener expiredObjectListener = new ExpiredObjectListener() {
            @Override
            public void onExpired(String name) {
                long auctionId  = Long.parseLong(name.split(":")[1]);
                Optional<AuctionRealtime> auctionRealtimeO = findById(auctionId);
                if (auctionRealtimeO.isEmpty()) {
                    throw new InvalidValueException("로직상 오류가 생겼습니다. 이미 종료된 경매입니다.");
                }
                AuctionRealtime auctionRealtime = auctionRealtimeO.get();

                auctionEndService.auctionEnd(auctionRealtime, getExpireKey(auctionId));
            }
        };
        return expiredObjectListener;
    }

}
