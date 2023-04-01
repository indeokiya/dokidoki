package com.dokidoki.bid.api.controller;

import com.dokidoki.bid.api.service.BiddingService;
import com.dokidoki.bid.common.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MyAuctionController {

    private final BiddingService biddingService;

    @GetMapping("my-infos/bids")
    public ResponseEntity<?> getAuctionBidding(HttpServletRequest request) {
        long memberId = JWTUtil.getUserId(request);
        return ResponseEntity.ok(biddingService.auctionBiddingList(memberId));
    }

}
