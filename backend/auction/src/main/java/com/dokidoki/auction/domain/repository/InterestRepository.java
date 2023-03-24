package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.domain.entity.InterestEntity;
import com.dokidoki.auction.domain.entity.MemberEntity;
import com.dokidoki.auction.dto.response.InterestMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<InterestEntity, Long> {

    InterestEntity findByMemberEntityAndAuctionIngEntity(MemberEntity memberEntity, AuctionIngEntity auctionIngEntity);

    List<InterestMapping> findAllByMemberEntity_Id(Long memberId);
}
