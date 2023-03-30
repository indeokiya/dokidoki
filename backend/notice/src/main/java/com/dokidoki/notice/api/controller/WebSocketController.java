package com.dokidoki.notice.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendAlert(long memberId, String payload) {
        simpMessagingTemplate.convertAndSend("/topic/notice/"+memberId, payload);
    }

    public void sendAuctionInfo(long auctionId, String payload) {
        log.info("sending socketPriceSizeResp: {}", payload);
        // 최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감
        simpMessagingTemplate.convertAndSend("/topic/auctions/"+auctionId+"/realtime", payload);
    }

//    @Scheduled(cron = "*/10 * * * * *")
//    public void test() {
//        // 최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감
//        simpMessagingTemplate.convertAndSend("/topic/auctions/"+1+"/realtime", "test");
//    }

}
