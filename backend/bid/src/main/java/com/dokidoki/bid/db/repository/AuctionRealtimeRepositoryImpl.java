package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.db.entity.AuctionRealtime;
import org.redisson.api.RLiveObject;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class AuctionRealtimeRepositoryImpl implements AuctionRealtimeRepository{

    private RLiveObjectService liveObjectService;

    @Autowired
    public AuctionRealtimeRepositoryImpl(RedissonClient redisson) {
        this.liveObjectService = redisson.getLiveObjectService();
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

    @Transactional
    @Override
    public AuctionRealtime save(AuctionRealtime auctionRealtime) {
        return liveObjectService.persist(auctionRealtime);
    }

    @Transactional
    @Override
    public AuctionRealtime save(AuctionRealtime auctionRealtime, Duration duration) {
        AuctionRealtime res = liveObjectService.persist(auctionRealtime);
        RLiveObject rLiveObject = liveObjectService.asLiveObject(auctionRealtime);
        rLiveObject.expire(duration);
        return res;
    }


}
