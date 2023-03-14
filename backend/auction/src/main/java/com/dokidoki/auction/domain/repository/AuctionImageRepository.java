package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionImageRepository extends JpaRepository<AuctionImage, Long> {
    List<AuctionImage> findAuctionImagesByAuctionId(Long auctionId);
}
