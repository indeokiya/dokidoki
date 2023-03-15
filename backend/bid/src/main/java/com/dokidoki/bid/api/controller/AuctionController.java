package com.dokidoki.bid.api.controller;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.dokidoki.bid.api.request.AuctionUpdatePriceSizeReq;
import com.dokidoki.bid.api.response.LeaderBoardMemberResp;
import com.dokidoki.bid.api.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @MessageMapping("/auctions/{auctionId}/leaderboard") // client 가 접근하는 URL
    @SendTo("/topic/auctions/{auctionId}/leaderboard") // kafka 에서 데이터를 보내주는 URL
    public ResponseEntity<?> getLeaderBoard(@DestinationVariable long auctionId, @Payload List<LeaderBoardMemberResp> list) {

        return ResponseEntity.ok(list);
    }

}
