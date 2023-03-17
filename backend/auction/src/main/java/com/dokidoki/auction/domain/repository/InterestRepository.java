package com.dokidoki.auction.domain.repository;

import com.dokidoki.auction.domain.entity.AuctionIng;
import com.dokidoki.auction.domain.entity.Interest;
import com.dokidoki.auction.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {

    Interest findByMemberAndAuctionIng(Member member, AuctionIng auctionIng);


}
