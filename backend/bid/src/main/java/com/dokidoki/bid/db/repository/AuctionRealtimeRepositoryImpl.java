package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.db.entity.AuctionRealtime;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Repository
public class AuctionRealtimeRepositoryImpl implements AuctionRealtimeRepository{

    private RedissonClient redisson;
    private RLiveObjectService liveObjectService;

    @Autowired
    public AuctionRealtimeRepositoryImpl(RedissonClient redisson) {
        this.liveObjectService = redisson.getLiveObjectService();
        this.redisson = redisson;
    }

    @Override
    public Optional<AuctionRealtime> findById(long auctionId) {
        AuctionRealtime auctionRealtime = liveObjectService.get(AuctionRealtime.class, auctionId);
        if (auctionRealtime == null) {
            return Optional.empty();
        } else {
            return Optional.of(auctionRealtime);
        }
    }

    @Override
    public AuctionRealtime save(AuctionRealtime auctionRealtime) {
        RTransaction transaction = redisson.createTransaction(TransactionOptions.defaults());
        try {
            AuctionRealtime res = liveObjectService.persist(auctionRealtime);
            transaction.commit();
            return res;
        } catch(TransactionException e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public AuctionRealtime save(AuctionRealtime auctionRealtime, Duration duration) {
        RTransaction transaction = redisson.createTransaction(TransactionOptions.defaults());
        try {
            AuctionRealtime res = liveObjectService.persist(auctionRealtime);
            RLiveObject rLiveObject = liveObjectService.asLiveObject(auctionRealtime);
            rLiveObject.expire(duration);
            transaction.commit();
            return res;
        } catch (TransactionException e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void deleteAll() {
        RTransaction transaction = redisson.createTransaction(TransactionOptions.defaults());
        try{
            Iterable<Long> iterable = liveObjectService.findIds(AuctionRealtime.class);
            iterable.forEach(id ->
                    liveObjectService.delete(AuctionRealtime.class, id)
            );
            transaction.commit();
        } catch (TransactionException e) {
            transaction.rollback();
            throw e;
        }
    }


}
