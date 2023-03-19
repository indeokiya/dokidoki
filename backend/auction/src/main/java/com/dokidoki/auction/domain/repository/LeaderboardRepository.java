package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.LeaderboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderboardRepository extends JpaRepository<LeaderboardEntity, Long> {
    // 한 경매의 리더보드 검색
    List<LeaderboardEntity> findLeaderboardEntitiesByAuctionIdOrderByBidTime(Long auctionId);
}
