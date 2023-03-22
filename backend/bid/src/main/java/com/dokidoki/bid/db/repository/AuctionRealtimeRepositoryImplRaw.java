package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.db.entity.AuctionRealtime;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

//@RequiredArgsConstructor
//@Repository
public class AuctionRealtimeRepositoryImplRaw implements AuctionRealtimeRepository{

//    private final RedissonClient redisson;




    @Override
    public Optional<AuctionRealtime> findById(long auctionId) {

        return Optional.empty();
    }

    @Override
    public AuctionRealtime save(AuctionRealtime auctionRealtime) {

        return null;
    }

    @Override
    public AuctionRealtime save(AuctionRealtime auctionRealtime, Duration duration) {

        return null;
    }

    @Override
    public void deleteAll() {

    }
}
