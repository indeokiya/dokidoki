package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.ProductEntity;
import com.dokidoki.auction.domain.entity.TreasuryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreasuryRepository extends JpaRepository<TreasuryEntity, Long> {
}
