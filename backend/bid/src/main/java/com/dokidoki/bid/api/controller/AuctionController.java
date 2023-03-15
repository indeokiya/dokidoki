package com.dokidoki.bid.api.controller;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.dokidoki.bid.api.request.AuctionUpdatePriceSizeReq;
import com.dokidoki.bid.api.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("auctions")
public class AuctionController {

    private final LeaderBoardService leaderBoardService;

    @PostMapping("/{auctionId}/bid")
    public ResponseEntity<?> bid(@RequestBody AuctionBidReq req, @PathVariable() long auctionId) {
        Map<String, Object> resultMap = new HashMap<>();
        leaderBoardService.bid(auctionId, req);
        resultMap.put("status_code", 200);
        resultMap.put("message", "성공");
        return ResponseEntity.ok(resultMap);
    }

    @PutMapping("/{auctionId}/price-size")
    public ResponseEntity<?> updatePriceSize(@RequestBody AuctionUpdatePriceSizeReq req, @PathVariable long auctionId) {
        Map<String, Object> resultMap = new HashMap<>();
        leaderBoardService.updatePriceSize(auctionId, req);
        resultMap.put("status_code", 200);
        resultMap.put("message", "성공");
        return ResponseEntity.ok(resultMap);
    }

}
