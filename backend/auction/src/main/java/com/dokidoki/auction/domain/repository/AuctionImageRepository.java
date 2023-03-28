package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionImageEntity;
import com.dokidoki.auction.dto.response.ImageInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionImageRepository extends JpaRepository<AuctionImageEntity, Long> {
    List<AuctionImageEntity> findAuctionImagesByAuctionId(Long auctionId);

    @Query(value = "SELECT a.auction_id, a.image_url " +
            "FROM (" +
            "   SELECT sa.auction_id, sa.image_url," +
            "       ROW_NUMBER() OVER (PARTITION BY auction_id ORDER BY sa.id ASC) as row_num " +
            "   FROM auction_image sa " +
            ") as a " +
            "WHERE row_num = 1 AND a.auction_id IN :auctionIdList " +
            "ORDER BY a.auction_id DESC ", nativeQuery = true)
    List<ImageInterface> findImagesByAuctionIdIn(@Param("auctionIdList") List<Long> auctionIdList);

    @Modifying
    @Query("delete from AuctionImageEntity a where a.auctionId=:auction_id")
    void deleteAuctionImagesByAuctionId(@Param("auction_id") Long auction_id);
}
