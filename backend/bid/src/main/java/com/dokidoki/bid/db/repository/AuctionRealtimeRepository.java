package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.db.entity.AuctionRealtime;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface AuctionRealtimeRepository {

    Optional<AuctionRealtime> findById(long auctionId);

    AuctionRealtime save(AuctionRealtime auctionRealtime);

    AuctionRealtime save(AuctionRealtime auctionRealtime, Duration duration);

    void deleteAll();

}
