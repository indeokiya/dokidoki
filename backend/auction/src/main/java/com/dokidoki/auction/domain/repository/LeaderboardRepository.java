package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.LeaderboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderboardRepository extends JpaRepository<LeaderboardEntity, Long> {

}
