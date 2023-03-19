package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionIngRepository extends JpaRepository<AuctionIngEntity, Long> {
}
