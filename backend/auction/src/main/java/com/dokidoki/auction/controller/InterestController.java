package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.service.AuctionService;
import com.dokidoki.auction.service.InterestService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/auctions/interests")
@RequiredArgsConstructor
@RestController
public class InterestController {

    private final InterestService interestService;

    @PostMapping("")
    public ResponseEntity<BaseResponseBody> addInterest(
            @RequestParam("auction_id") @ApiParam(value = "경매 id", required = true) Long auctionId) {

        Long buyerId = 1L;
        interestService.addInterest(buyerId, auctionId);
        return ResponseEntity.status(201).body(BaseResponseBody.of("관심목록에 추가되었습니다."));
    }

}
