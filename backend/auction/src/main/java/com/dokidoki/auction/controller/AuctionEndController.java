package com.dokidoki.auction.controller;

import com.dokidoki.auction.dto.response.AuctionEndResponse;
import com.dokidoki.auction.dto.response.CommonResponse;
import com.dokidoki.auction.service.AuctionEndService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuctionEndController {
    private final AuctionEndService auctionEndService;

    @GetMapping("/auctions/end/{auction_id}")
    public ResponseEntity<CommonResponse<AuctionEndResponse>> readAuctionEnd(@PathVariable Long auction_id) {
        return new ResponseEntity<>(
                CommonResponse.of(200, "성공", auctionEndService.readAuctionEnds(auction_id)),
                HttpStatus.OK
        );
    }
}
