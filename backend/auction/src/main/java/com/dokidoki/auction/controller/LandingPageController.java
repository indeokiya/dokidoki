package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.dto.response.MostSaleProductContent;
import com.dokidoki.auction.service.LandingPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class LandingPageController {
    private final LandingPageService landingPageService;

    // 총 거래금액 조회
    @GetMapping("/total-prices")
    public ResponseEntity<BaseResponseBody> getTotalPrice() {
        Long totalPrice = landingPageService.getTotalPrice();
        return ResponseEntity.status(200).body(BaseResponseBody.of("총 거래금액 조회 성공", totalPrice));
    }

    // 가장 많이 거래된 제품 조회하기, 최대 5개
    @GetMapping("/most-sale-products")
    public ResponseEntity<BaseResponseBody> readMostSaleProducts(
            @RequestParam(defaultValue = "0") Integer days) {
        String message;
        List<MostSaleProductContent> mostSaleProductContents;
        if (days == 0) {
            message = "가장 많이 거래된 제품 조회 성공";
            mostSaleProductContents = landingPageService.readMostSaleProducts();
        } else {
            message = days + "일 이내 가장 많이 거래된 제품 조회 성공";
            mostSaleProductContents = landingPageService.readMostSaleProductsWithinNDays(days);
        }
        return ResponseEntity.status(200).body(
                BaseResponseBody.of(message, mostSaleProductContents)
        );
    }

    // 수수료로 얻은 총 수익금 조회
    @GetMapping("/total-treasury")
    public ResponseEntity<BaseResponseBody> readTotalTreasury() {
        return ResponseEntity.status(200).body(
                BaseResponseBody.of("국고 조회 성공", landingPageService.readTotalTreasury())
        );
    }
}
