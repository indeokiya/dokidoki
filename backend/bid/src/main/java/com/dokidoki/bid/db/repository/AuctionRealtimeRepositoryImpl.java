package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.common.codes.RealTimeConstants;
import com.dokidoki.bid.db.entity.AuctionRealtime;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapCache;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class AuctionRealtimeRepositoryImpl implements AuctionRealtimeRepository{

    private final RedissonClient redisson;
    private String key = RealTimeConstants.key;
    private RMapCache<Long, AuctionRealtime> map;

    @Autowired
    public void setAuctionRealtimeRepositoryImpl() {
        this.key = RealTimeConstants.key;
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
    public void save(AuctionRealtime auctionRealtime) {
        RTransaction transaction = redisson.createTransaction(TransactionOptions.defaults());
        try {
            map.put(auctionRealtime.getAuctionId(), auctionRealtime);
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void save(AuctionRealtime auctionRealtime, long ttl, TimeUnit timeUnit) {
        RTransaction transaction = redisson.createTransaction(TransactionOptions.defaults());
        try {
            map.put(auctionRealtime.getAuctionId(), auctionRealtime, ttl, timeUnit);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public boolean deleteAll() {
        RTransaction transaction = redisson.createTransaction(TransactionOptions.defaults());
        try {
            boolean res = map.delete();
            transaction.commit();
            return res;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
