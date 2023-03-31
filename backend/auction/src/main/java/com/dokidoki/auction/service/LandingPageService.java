package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LandingPageService {
    private final AuctionEndRepository auctionEndRepository;

    // 총 거래금액 조회
    @Transactional(readOnly = true)
    public Long getTotalPrice() {
        Long totalPrice = auctionEndRepository.getTotalPrice();
        return totalPrice != null ? totalPrice : 0;
    }
}
