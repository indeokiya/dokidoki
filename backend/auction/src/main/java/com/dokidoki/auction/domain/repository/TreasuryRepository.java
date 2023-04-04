package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.ProductEntity;
import com.dokidoki.auction.domain.entity.TreasuryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TreasuryRepository extends JpaRepository<TreasuryEntity, Long> {
    @Query("SELECT SUM(t.money) FROM TreasuryEntity t")
    Long getTotalTreasury();
}
