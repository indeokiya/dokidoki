package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.AuctionIng;
import com.dokidoki.auction.domain.entity.Interest;
import com.dokidoki.auction.domain.entity.Member;
import com.dokidoki.auction.domain.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class InterestService {

    private final MemberService memberService;

    private final AuctionService auctionService;

    private final InterestRepository interestRepository;

    @Transactional
    public boolean addInterest(Long memberId, Long auctionId) {

        Member member = memberService.getMemberById(memberId);
        AuctionIng auctionIng = auctionService.getAuctioningById(auctionId);

        Interest exInterest = interestRepository.findByMemberAndAuctionIng(member, auctionIng);

        // 이미 관심경매로 등록되어 있는 경우
        if (exInterest != null)
            return false;

        Interest interest =  Interest.builder()
                .member(member)
                .auctionIng(auctionIng)
                .build();

        // 관심경매로 등록
        interestRepository.save(interest);
        return true;
    }


    @Transactional
    public boolean deleteInterest(Long memberId, Long auctionId) {

        Member member = memberService.getMemberById(memberId);
        AuctionIng auctionIng = auctionService.getAuctioningById(auctionId);
        Interest interest = interestRepository.findByMemberAndAuctionIng(member, auctionIng);

        // 관심등록이 안되어있을 경우
        if (interest == null) {
            return false;
        }

        log.debug("member = {}", member);
        log.debug("auctionIng = {}", auctionIng);
        interestRepository.delete(interest);

        return true;
    }

}
