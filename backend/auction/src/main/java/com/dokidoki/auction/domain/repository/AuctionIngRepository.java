package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.dto.response.SimpleAuctionEndInterface;
import com.dokidoki.auction.dto.response.SimpleAuctionIngInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AuctionIngRepository extends JpaRepository<AuctionIngEntity, Long> {
    @Query("SELECT a.id as auction_id, a.title as auction_title " +
            " , a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offer_price, a.highestPrice as cur_price " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "ORDER BY a.endAt DESC ")
    Page<SimpleAuctionIngInterface> findAllSimpleIngList(Pageable pageable);

    @Query("SELECT a.id as auction_id, a.title as auction_title " +
            " , a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offer_price, a.highestPrice as cur_price " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "WHERE a.endAt < :within_an_hour " +
            "ORDER BY a.endAt DESC ")
    Page<SimpleAuctionIngInterface> findAuctionIngEntitiesByEndAtLessThan(
            @Param("within_an_hour") LocalDateTime withinAnHour, Pageable pageable);
    default Page<SimpleAuctionIngInterface> findAllSimpleDeadlineList(Pageable pageable) {
        // 현재로부터 한 시간 뒤의 시간을 구한 뒤, endAt과 비교 연산
        return findAuctionIngEntitiesByEndAtLessThan(LocalDateTime.now().plusMinutes(60), pageable);
    }

    @Query(value = "SELECT a.id as auction_id, a.title as auction_title " +
            " , a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offerPrice, a.highestPrice as cur_price " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "WHERE (p.name LIKE %:keyword% or c.categoryName LIKE %:keyword% or a.title LIKE %:keyword%) " +
            " AND c.id = :category_id " +
            "ORDER BY a.endAt DESC ")
    Page<SimpleAuctionIngInterface> findAllSimpleIngListByKeywordANDCategoryId(
            @Param("keyword") String keyword, @Param("category_id") Long categoryId, Pageable pageable);

    @Query(value = "SELECT a.id as auction_id, a.title as auction_title " +
            " , a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offer_price, a.highestPrice as cur_price " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "WHERE (p.name LIKE %:keyword% or c.categoryName LIKE %:keyword% or a.title LIKE %:keyword%) " +
            "ORDER BY a.endAt DESC ")
    Page<SimpleAuctionIngInterface> findAllSimpleIngListByKeyword(
            @Param("keyword") String keyword, Pageable pageable);
}
