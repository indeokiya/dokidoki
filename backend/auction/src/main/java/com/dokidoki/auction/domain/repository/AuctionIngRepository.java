package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionIng;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionIngRepository extends JpaRepository<AuctionIng, Long> {
}
