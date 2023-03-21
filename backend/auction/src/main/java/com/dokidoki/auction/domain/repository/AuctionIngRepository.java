package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.dto.response.SimpleAuctionEndInterface;
import com.dokidoki.auction.dto.response.SimpleAuctionIngInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionIngRepository extends JpaRepository<AuctionIngEntity, Long> {
    @Query(value = "SELECT a.id as auction_id, a.title as auction_title " +
            " , a.end_at as end_time, p.name as product_name, c.category_name as category_name " +
            " , a.offer_price as offer_price, a.highest_price as cur_price " +
            "FROM auction_ing a " +
            " JOIN product p " +
            " JOIN category c " +
            "WHERE a.product_id = p.id" +
            " AND p.category_id = c.id " +
            "ORDER BY a.end_at DESC ", nativeQuery = true)
    Page<SimpleAuctionIngInterface> findAllSimpleIngList(Pageable pageable);

    @Query(value = "SELECT a.id as auction_id, a.title as auction_title " +
            " , a.end_at as end_time, p.name as product_name, c.category_name as category_name " +
            " , a.offer_price as offer_price, a.highest_price as cur_price " +
            "FROM auction_ing a " +
            " JOIN product p " +
            " JOIN category c " +
            "WHERE a.product_id = p.id" +
            " AND p.category_id = c.id " +
            " AND TIMESTAMPDIFF(second, now(), a.end_at) <= 3600 " +
            "ORDER BY a.end_at DESC ", nativeQuery = true)
    Page<SimpleAuctionIngInterface> findAllSimpleDeadlineList(Pageable pageable);

    @Query(value = "SELECT a.id as auction_id, a.title as auction_title " +
            " , a.end_at as end_time, p.name as product_name, c.category_name as category_name " +
            " , a.offer_price as offer_price, a.highest_price as cur_price " +
            "FROM auction_ing a " +
            " JOIN product p " +
            " JOIN category c " +
            "WHERE a.product_id = p.id" +
            " AND p.category_id = c.id " +
            " AND (p.name LIKE %:keyword% or c.category_name LIKE %:keyword% or a.title LIKE %:keyword%) " +
            " AND c.id = :category_id " +
            "ORDER BY a.end_at DESC ", nativeQuery = true)
    Page<SimpleAuctionIngInterface> findAllSimpleIngListByKeywordANDCategoryId(
            @Param("keyword") String keyword, @Param("category_id") Long categoryId, Pageable pageable);

    @Query(value = "SELECT a.id as auction_id, a.title as auction_title " +
            " , a.end_at as end_time, p.name as product_name, c.category_name as category_name " +
            " , a.offer_price as offer_price, a.highest_price as cur_price " +
            "FROM auction_ing a " +
            " JOIN product p " +
            " JOIN category c " +
            "WHERE a.product_id = p.id" +
            " AND p.category_id = c.id " +
            " AND (p.name LIKE %:keyword% or c.category_name LIKE %:keyword% or a.title LIKE %:keyword%) " +
            "ORDER BY a.end_at DESC ", nativeQuery = true)
    Page<SimpleAuctionIngInterface> findAllSimpleIngListByKeyword(
            @Param("keyword") String keyword, Pageable pageable);
}
