package com.dokidoki.auction.service;

import com.dokidoki.auction.common.error.exception.InvalidValueException;
import com.dokidoki.auction.domain.entity.AuctionIng;
import com.dokidoki.auction.domain.entity.Interest;
import com.dokidoki.auction.domain.entity.Member;
import com.dokidoki.auction.domain.repository.AuctionIngRepository;
import com.dokidoki.auction.domain.repository.InterestRepository;
import com.dokidoki.auction.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class InterestService {

    private final MemberRepository memberRepository;

    private final AuctionIngRepository auctionIngRepository;

    private final InterestRepository interestRepository;

    public void addInterest(Long memberId, Long auctionId) {

        Optional<Member> memberO = memberRepository.findById(memberId);
        Optional<AuctionIng> auctionIngO = auctionIngRepository.findById(auctionId);

        // 사용자 유무 체크
        if (memberO.isEmpty()) {
            throw new InvalidValueException("member_id가 존재하지 않습니다.");
        }

        // 진행중 경매 유무 체크
        if (auctionIngO.isEmpty()) {
            throw new InvalidValueException("auction_id가 존재하지 않습니다.");
        }

        Interest interest =  Interest.builder()
                .member(memberO.get())
                .auctionIng(auctionIngO.get())
                .build();

        interestRepository.save(interest);
    }

}
