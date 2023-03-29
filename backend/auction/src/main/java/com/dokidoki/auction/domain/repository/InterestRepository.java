package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.domain.entity.InterestEntity;
import com.dokidoki.auction.domain.entity.MemberEntity;
import com.dokidoki.auction.dto.response.InterestMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<InterestEntity, Long> {
    InterestEntity findByMemberEntity_IdAndAuctionIngEntity_Id(
            @Param("member_id") Long memberId, @Param("auction_id") Long auctionId);

    List<InterestMapping> findAllByMemberEntity_Id(Long memberId);
}
