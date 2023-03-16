package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionImageRepository extends JpaRepository<AuctionImage, Long> {
    List<AuctionImage> findAuctionImagesByAuctionId(Long auctionId);

    @Modifying
    @Query("delete from AuctionImage a where a.auctionId=:auction_id")
    void deleteAuctionImagesByAuctionId(@Param("auction_id") Long auction_id);
}
