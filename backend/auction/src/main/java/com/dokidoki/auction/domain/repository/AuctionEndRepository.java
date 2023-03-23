package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionEndEntity;
import com.dokidoki.auction.dto.response.DetailAuctionEndInterface;
import com.dokidoki.auction.dto.response.MyHistoryInfoInterface;
import com.dokidoki.auction.dto.response.SimpleAuctionEndInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionEndRepository extends JpaRepository<AuctionEndEntity, Long> {
    // 경매 End, 제품, 카테고리, 사용자 테이블 조인해서 데이터 검색하기
    @Query("SELECT a.title as auction_title, a.startTime as start_time, a.endTime as end_time, a.description as description " +
            ", a.offerPrice as offer_price, a.finalPrice as final_price, s.name as seller_name, b.name as buyer_name " +
            ", p.name as product_name, c.categoryName as category_name " +
            "FROM AuctionEndEntity a " +
            " JOIN a.product p " +
            " JOIN p.categoryEntity c " +
            " JOIN a.buyer b " +
            " JOIN a.seller s " +
            "WHERE a.id = :auction_id")
    List<DetailAuctionEndInterface> findDetailAuctionEndEntitiesById(@Param("auction_id") Long auctionId);
    
    @Query("SELECT a.id as auction_id, a.title as auction_title, a.startTime as start_time, a.endTime as end_time" +
            ", a.offerPrice as offer_price, a.finalPrice as final_price, a.buyer.id as buyer_id" +
            ", p.name as product_name, c.categoryName as category_name " +
            "FROM AuctionEndEntity a " +
            " JOIN a.product p " +
            " JOIN p.categoryEntity c " +
            "ORDER BY a.endTime DESC ")
    Page<SimpleAuctionEndInterface> findAllSimpleEndList(Pageable pageable);

    /*
    특정 사용자가 과거에 구매했던 내역 조회
     */
    @Query("SELECT a.id as auction_id, a.endTime as end_time, a.offerPrice as offer_price, a.finalPrice as final_price " +
            ", b.name as buyer_name, s.name as seller_name" +
            ", p.name as product_name, c.categoryName as category_name " +
            "FROM AuctionEndEntity a " +
            " JOIN a.product p " +
            " JOIN p.categoryEntity c " +
            " JOIN a.buyer b " +
            " JOIN a.seller s " +
            "WHERE a.buyer.id = :member_id " +
            "ORDER BY auction_id DESC ")
    Page<MyHistoryInfoInterface> findAllMyPurchases(@Param("member_id") Long memberId, Pageable pageable);

    /*
    특정 사용자가 과거에 판매했던 내역 조회
     */
    @Query("SELECT a.id as auction_id, a.endTime as end_time, a.offerPrice as offer_price, a.finalPrice as final_price " +
            ", b.name as buyer_name, s.name as seller_name" +
            ", p.name as product_name, c.categoryName as category_name " +
            "FROM AuctionEndEntity a " +
            " JOIN a.product p " +
            " JOIN p.categoryEntity c " +
            " JOIN a.buyer b " +
            " JOIN a.seller s " +
            "WHERE a.seller.id = :member_id " +
            "ORDER BY auction_id DESC ")
    Page<MyHistoryInfoInterface> findAllMySales(@Param("member_id") Long memberId, Pageable pageable);

    /*
    총 구매가 조회
     */
    @Query("SELECT SUM(a.finalPrice) FROM AuctionEndEntity a WHERE a.buyer.id = :member_id")
    Long getMyTotalOfPurchases(@Param("member_id") Long memberId);

    /*
    총 판매가 조회
     */
    @Query("SELECT SUM(a.finalPrice) FROM AuctionEndEntity a WHERE a.seller.id = :member_id")
    Long getMyTotalOfSales(@Param("member_id") Long memberId);
}