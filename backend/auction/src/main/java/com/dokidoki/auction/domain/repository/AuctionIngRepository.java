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
    // 진행중인 경매의 상세정보 조회
    AuctionIngEntity findAuctionIngEntityByIdOrderById(Long auctionId);

    // 마감임박 경매 목록, 6시간
    @Query("SELECT a FROM AuctionIngEntity a WHERE a.endAt <= :within_six_hours ORDER BY a.id DESC ")
    Page<AuctionIngEntity> findAuctionIngEntitiesByEndAtLessThan(
            @Param("within_six_hours") LocalDateTime withinSixHours, Pageable pageable);
    default Page<AuctionIngEntity> findAllSimpleDeadlineList(Pageable pageable) {
        // 현재로부터 6시간 뒤의 시간을 구한 뒤, endAt과 비교 연산
        return findAuctionIngEntitiesByEndAtLessThan(LocalDateTime.now().plusHours(6L), pageable);
    }

    // 카테고리 & 키워드로 진행중인 경매 목록 검색 (동적쿼리로 아래 조회 메서드랑 합치는 게 좋을 듯)
    @Query("SELECT a FROM AuctionIngEntity a JOIN a.productEntity p JOIN p.categoryEntity c " +
            "WHERE (p.name LIKE %:keyword% or c.categoryName LIKE %:keyword% or a.title LIKE %:keyword%) " +
            " AND c.id = :category_id " +
            "ORDER BY a.id DESC ")
    Page<AuctionIngEntity> findAllSimpleIngListByKeywordANDCategoryId(
            @Param("keyword") String keyword, @Param("category_id") Long categoryId, Pageable pageable);

    // 키워드로 진행중인 경매 목록 검색
    @Query("SELECT a FROM AuctionIngEntity a JOIN a.productEntity p JOIN p.categoryEntity c " +
            "WHERE (p.name LIKE %:keyword% or c.categoryName LIKE %:keyword% or a.title LIKE %:keyword%) " +
            "ORDER BY a.id DESC ")
    Page<AuctionIngEntity> findAllSimpleIngListByKeyword(
            @Param("keyword") String keyword, Pageable pageable);

    // 특정 사용자가 판매중인 경매 목록 조회
    @Query("SELECT a FROM AuctionIngEntity a WHERE a.seller.id = :member_id ORDER BY a.id DESC ")
    Page<AuctionIngEntity> findAllMySellingAuction(@Param("member_id") Long memberId, Pageable pageable);

    // 특정 사용자가 입찰중인 경매 목록 조회
    @Query("SELECT a FROM AuctionIngEntity a " +
            "WHERE :member_id IN (SELECT DISTINCT l.memberEntity.id FROM LeaderboardEntity l WHERE l.auctionId = a.id) " +
            "ORDER BY a.id DESC ")
    Page<AuctionIngEntity> findAllMyBiddingAuction(@Param("member_id") Long memberId, Pageable pageable);

    // 특정 사용자가 관심 갖는 경매 목록 조회
    @Query("SELECT a FROM AuctionIngEntity a " +
            "WHERE :member_id IN (SELECT DISTINCT i.memberEntity.id FROM InterestEntity i WHERE i.auctionIngEntity.id = a.id) " +
            "ORDER BY a.id DESC ")
    Page<AuctionIngEntity> findAllMyInterestingAuction(@Param("member_id") Long memberId, Pageable pageable);

    // 특정 사용자가 판매중인 경매 ID 조회
    List<AuctionIngMapping> findAuctionIngEntityBySeller_Id(Long sellerId);
}
