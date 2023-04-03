package com.dokidoki.notice.db.repository;


import com.dokidoki.notice.db.entity.AuctionRealtime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public interface AuctionRealtimeRepository {

    Optional<AuctionRealtime> findById(Long auctionId);

    boolean deleteAll();

    boolean isExpired(Long auctionId);

    void delete(Long auctionId);

}