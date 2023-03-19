package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionEndEntity;
import com.dokidoki.auction.dto.response.AuctionEndInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionEndRepository extends JpaRepository<AuctionEndEntity, Long> {
    // 경매 End, 제품, 카테고리, 사용자 테이블 조인해서 데이터 검색하기 (최적화 필요할 것 같긴 한데 일단은..)
    @Query(value = "SELECT a.title as auction_title, a.start_time as start_time, a.end_time as end_time" +
            " , p.name as product_name, c.category_name as category_name, a.description as description" +
            " , a.offer_price as offer_price, a.final_price as final_price" +
            " , s.name as seller_name, b.name as buyer_name " +
            "FROM auction_end a" +
            " JOIN product p" +
            " JOIN category c" +
            " JOIN member s" +
            " JOIN member b " +
            "WHERE a.id = :auction_id" +
            " AND a.product_id = p.id" +
            " AND p.category_id = c.id" +
            " AND a.seller_id = s.id" +
            " AND a.buyer_id = b.id", nativeQuery = true)
    AuctionEndInterface findAuctionEndEntitiesById(@Param("auction_id") Long auctionId);
}