package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionIngRepository extends JpaRepository<AuctionIngEntity, Long> {
    // 경매 End, 제품, 카테고리, 사용자 테이블 조인해서 데이터 검색하기
    /*
    진행중인 경매의 상세정보 조회
     */
    @Query("SELECT a.title as auction_title, a.startTime as start_time, a.endAt as end_time, a.description as description " +
            ", a.meetingPlace as meeting_place, a.priceSize as price_size, a.offerPrice as offer_price " +
            ", a.highestPrice as highest_price, s.name as seller_name, s.id as seller_id " +
            ", p.name as product_name, c.categoryName as category_name " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            " JOIN a.seller s " +
            "WHERE a.id = :auction_id " +
            "ORDER BY a.id DESC ")
    DetailAuctionIngInterface findAuctionIngEntityById(@Param("auction_id") Long auctionId);

    /*
    진행중인 경매 목록 (전체)
     */
    @Query("SELECT a.id as auction_id, a.title as auction_title, a.meetingPlace as meeting_place " +
            " , a.startTime as start_time, a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offer_price, a.highestPrice as cur_price, a.seller.id as seller_id, a.priceSize as price_size " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "ORDER BY a.id DESC ")
    Page<SimpleAuctionIngInterface> findAllSimpleIngList(Pageable pageable);

    /*
    마감임박 경매 목록, 1시간
     */
    @Query("SELECT a.id as auction_id, a.title as auction_title, a.meetingPlace as meeting_place " +
            " , a.startTime as start_time, a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offer_price, a.highestPrice as cur_price, a.seller.id as seller_id, a.priceSize as price_size " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "WHERE a.endAt < :within_an_hour " +
            "ORDER BY a.id DESC ")
    Page<SimpleAuctionIngInterface> findAuctionIngEntitiesByEndAtLessThan(
            @Param("within_an_hour") LocalDateTime withinAnHour, Pageable pageable);
    default Page<SimpleAuctionIngInterface> findAllSimpleDeadlineList(Pageable pageable) {
        // 현재로부터 한 시간 뒤의 시간을 구한 뒤, endAt과 비교 연산
        return findAuctionIngEntitiesByEndAtLessThan(LocalDateTime.now().plusMinutes(60), pageable);
    }

    /*
    카테고리 & 키워드로 진행중인 경매 목록 검색 (동적쿼리로 아래 조회 메서드랑 합치는 게 좋을 듯)
     */
    @Query(value = "SELECT a.id as auction_id, a.title as auction_title, a.meetingPlace as meeting_place " +
            " , a.startTime as start_time, a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offerPrice, a.highestPrice as cur_price, a.seller.id as seller_id, a.priceSize as price_size " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "WHERE (p.name LIKE %:keyword% or c.categoryName LIKE %:keyword% or a.title LIKE %:keyword%) " +
            " AND c.id = :category_id " +
            "ORDER BY a.id DESC ")
    Page<SimpleAuctionIngInterface> findAllSimpleIngListByKeywordANDCategoryId(
            @Param("keyword") String keyword, @Param("category_id") Long categoryId, Pageable pageable);

    /*
    키워드로 진행중인 경매 목록 검색
     */
    @Query(value = "SELECT a.id as auction_id, a.title as auction_title, a.meetingPlace as meeting_place " +
            " , a.startTime as start_time, a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offer_price, a.highestPrice as cur_price, a.seller.id as seller_id, a.priceSize as price_size " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "WHERE (p.name LIKE %:keyword% or c.categoryName LIKE %:keyword% or a.title LIKE %:keyword%) " +
            "ORDER BY a.id DESC ")
    Page<SimpleAuctionIngInterface> findAllSimpleIngListByKeyword(
            @Param("keyword") String keyword, Pageable pageable);

    /*
    특정 사용자가 판매중인 경매 목록 조회
     */
    @Query("SELECT a.id as auction_id, a.title as auction_title, a.meetingPlace as meeting_place " +
            " , a.startTime as start_time, a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offer_price, a.highestPrice as cur_price, a.seller.id as seller_id, a.priceSize as price_size " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "WHERE a.seller.id = :member_id " +
            "ORDER BY a.id DESC ")
    Page<SimpleAuctionIngInterface> findAllMySellingAuction(@Param("member_id") Long memberId, Pageable pageable);

    /*
    특정 사용자가 입찰중인 경매 목록 조회
     */
    @Query("SELECT a.id as auction_id, a.title as auction_title, a.meetingPlace as meeting_place " +
            " , a.startTime as start_time, a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offer_price, a.highestPrice as cur_price, a.seller.id as seller_id, a.priceSize as price_size " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "WHERE :member_id IN " +
            " (SELECT DISTINCT l.memberEntity.id FROM LeaderboardEntity l WHERE l.auctionId = a.id) " +
            "ORDER BY a.id DESC ")
    Page<SimpleAuctionIngInterface> findAllMyBiddingAuction(@Param("member_id") Long memberId, Pageable pageable);

    /*
    특정 사용자가 관심 갖는 경매 목록 조회
     */
    @Query("SELECT a.id as auction_id, a.title as auction_title, a.meetingPlace as meeting_place " +
            " , a.startTime as start_time, a.endAt as end_time, p.name as product_name, c.categoryName as category_name " +
            " , a.offerPrice as offer_price, a.highestPrice as cur_price, a.seller.id as seller_id, a.priceSize as price_size " +
            "FROM AuctionIngEntity a " +
            " JOIN a.productEntity p " +
            " JOIN p.categoryEntity c " +
            "WHERE :member_id IN " +
            " (SELECT DISTINCT i.memberEntity.id FROM InterestEntity i WHERE i.auctionIngEntity.id = a.id) " +
            "ORDER BY a.id DESC ")
    Page<SimpleAuctionIngInterface> findAllMyInterestingAuction(@Param("member_id") Long memberId, Pageable pageable);

    /*
    특정 사용자가 판매중인 경매 ID 조회
     */
    List<AuctionIngMapping> findAuctionIngEntityBySeller_Id(Long sellerId);
}
