package com.dokidoki.bid.api.controller;

import com.dokidoki.bid.api.request.AuctionBidReq;
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
    public ResponseEntity<?> bid(@RequestBody AuctionBidReq auctionBidReq, @PathVariable() long auctionId){
        Map<String, Object> resultMap = new HashMap<>();
        leaderBoardService.bid(auctionId, auctionBidReq.getMemberId());

        return ResponseEntity.ok(resultMap);
    }




}
