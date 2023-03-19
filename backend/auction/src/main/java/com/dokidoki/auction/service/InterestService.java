package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.entity.AuctionIngEntity;
import com.dokidoki.auction.domain.entity.InterestEntity;
import com.dokidoki.auction.domain.entity.MemberEntity;
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

        MemberEntity memberEntity = memberService.getMemberById(memberId);
        AuctionIngEntity auctionIngEntity = auctionService.getAuctioningById(auctionId);

        InterestEntity exInterestEntity = interestRepository.findByMemberAndAuctionIng(memberEntity, auctionIngEntity);

        // 이미 관심경매로 등록되어 있는 경우
        if (exInterestEntity != null)
            return false;

        InterestEntity interestEntity =  InterestEntity.builder()
                .memberEntity(memberEntity)
                .auctionIngEntity(auctionIngEntity)
                .build();

        // 관심경매로 등록
        interestRepository.save(interestEntity);
        return true;
    }


    @Transactional
    public boolean deleteInterest(Long memberId, Long auctionId) {

        MemberEntity memberEntity = memberService.getMemberById(memberId);
        AuctionIngEntity auctionIngEntity = auctionService.getAuctioningById(auctionId);
        InterestEntity interestEntity = interestRepository.findByMemberAndAuctionIng(memberEntity, auctionIngEntity);

        // 관심등록이 안되어있을 경우
        if (interestEntity == null) {
            return false;
        }

        log.debug("member = {}", memberEntity);
        log.debug("auctionIng = {}", auctionIngEntity);
        interestRepository.delete(interestEntity);

        return true;
    }

}
