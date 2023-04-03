package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.db.entity.AuctionRealtime;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface AuctionRealtimeRepository {

    Optional<AuctionRealtime> findById(Long auctionId);

    void save(AuctionRealtime auctionRealtime);

    void save(AuctionRealtime auctionRealtime, Long ttl, TimeUnit timeUnit);

    boolean deleteAll();

    boolean isExpired(Long auctionId);

    void delete(Long auctionId);

}
