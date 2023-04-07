package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionEndEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionEndRepository extends JpaRepository<AuctionEndEntity, Long> {
    AuctionEndEntity findAuctionEndEntityById(Long auctionId);

    // 특정 사용자가 과거에 구매했던 내역 조회
    Page<AuctionEndEntity> findAllByBuyer_IdOrderByIdDesc(Long buyerId, Pageable pageable);

    // 특정 사용자가 과거에 판매했던 내역 조회
    Page<AuctionEndEntity> findAllBySeller_IdOrderByIdDesc(Long sellerId, Pageable pageable);

    // 총 구매가 조회
    @Query("SELECT SUM(a.finalPrice) FROM AuctionEndEntity a WHERE a.buyer.id = :member_id")
    Long getMyTotalOfPurchases(@Param("member_id") Long memberId);

    // 총 판매가 조회
    @Query("SELECT SUM(a.finalPrice) FROM AuctionEndEntity a WHERE a.seller.id = :member_id")
    Long getMyTotalOfSales(@Param("member_id") Long memberId);

    // 총 거래금액 조회
    @Query("SELECT SUM(a.finalPrice) FROM AuctionEndEntity a")
    Long getTotalPrice();
}