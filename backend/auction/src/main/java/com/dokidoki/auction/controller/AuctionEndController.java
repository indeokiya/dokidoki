package com.dokidoki.auction.controller;

import com.dokidoki.auction.common.BaseResponseBody;
import com.dokidoki.auction.service.AuctionEndService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuctionEndController {
    private final AuctionEndService auctionEndService;

    @GetMapping("/auctions/end/{auction_id}")
    public ResponseEntity<BaseResponseBody> readAuctionEnd(@PathVariable Long auction_id) {
        return ResponseEntity
                .status(200)
                .body(BaseResponseBody.of("성공", auctionEndService.readAuctionEnds(auction_id)));
    }
}
