package com.dokidoki.bid.api.controller;

import com.dokidoki.bid.api.response.LeaderBoardMemberResp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuctionSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("auctions/{auctionId}/bid-price") // 이쪽으로 메시지 보내면 함수가 호출됨
    public void getLeaderBoard(@DestinationVariable long auctionId, @Payload String message) {
        Map<String, Object> resultMap = new HashMap<>();
        System.out.println(message);
        System.out.println(auctionId);
        resultMap.put("auctionId", auctionId);
        resultMap.put("message", message);

        // 이 부분을 카프카로 처리해야 되는 듯? (최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감)
        simpMessagingTemplate.convertAndSend("/topic/auctions/"+auctionId+"/bid-price", resultMap);

    }

}
