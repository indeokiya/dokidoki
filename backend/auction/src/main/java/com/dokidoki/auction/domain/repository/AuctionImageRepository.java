package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionImageRepository extends JpaRepository<AuctionImageEntity, Long> {
    List<AuctionImageEntity> findAuctionImagesByAuctionId(Long auctionId);

    @Modifying
    @Query("delete from auction_image a where a.auctionId=:auction_id")
    void deleteAuctionImagesByAuctionId(@Param("auction_id") Long auction_id);
}
