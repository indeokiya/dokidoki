package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.domain.entity.InterestEntity;
import com.dokidoki.auction.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<InterestEntity, Long> {

    InterestEntity findByMemberAndAuctionIng(MemberEntity memberEntity, AuctionIngEntity auctionIngEntity);


}
