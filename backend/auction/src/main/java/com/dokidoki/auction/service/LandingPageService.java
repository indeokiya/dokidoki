package com.dokidoki.auction.service;

import com.dokidoki.auction.domain.repository.AuctionEndRepository;
import com.dokidoki.auction.domain.repository.ProductRepository;
import com.dokidoki.auction.dto.response.MostSaleProductContent;
import com.dokidoki.auction.dto.response.MostSaleProductInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LandingPageService {
    private final AuctionEndRepository auctionEndRepository;
    private final ProductRepository productRepository;

    // 총 거래금액 조회
    @Transactional(readOnly = true)
    public Long getTotalPrice() {
        Long totalPrice = auctionEndRepository.getTotalPrice();
        return totalPrice != null ? totalPrice : 0;
    }

    // 가장 많이 거래된 제품 조회하기, 최대 5개
    @Transactional(readOnly = true)
    public List<MostSaleProductContent> readMostSaleProducts() {
        // DB 조회
        List<MostSaleProductInterface> mostSaleProductInterfaces = productRepository
                .findMostSaleProducts(PageRequest.of(0, 5));

        // DTO 생성
        List<MostSaleProductContent> mostSaleProductContents = new ArrayList<>();
        for (MostSaleProductInterface mostSaleProductInterface : mostSaleProductInterfaces)
            mostSaleProductContents.add(new MostSaleProductContent(mostSaleProductInterface));

        return mostSaleProductContents;
    }
}
