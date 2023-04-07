package com.dokidoki.notice.api.controller;

import com.dokidoki.notice.api.request.UpdatePointSocketReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    public void sendAlert(long memberId, String payload) {
        simpMessagingTemplate.convertAndSend("/topic/notice/"+memberId, payload);
    }

    public void sendAuctionInfo(long auctionId, String payload) {
        log.info("sending socketPriceSizeResp: {}", payload);
        // 최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감
        simpMessagingTemplate.convertAndSend("/topic/auctions/"+auctionId+"/realtime", payload);
    }

    public void sendUpdatedPointInfo(List<UpdatePointSocketReq> updatePointSocketReqs) {
        log.info("point update socket send");
        updatePointSocketReqs.forEach(
                (req)->{
                    log.info("소켓 요청 발사! : " + req.toString());
                    try {
                        simpMessagingTemplate.convertAndSend("/topic/points/"+req.getUser_id()+"/realtime",
                                objectMapper.writeValueAsString(req));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }



//    @Scheduled(cron = "*/10 * * * * *")
//    public void test() {
//        // 최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감
//        simpMessagingTemplate.convertAndSend("/topic/auctions/"+1+"/realtime", "test");
//    }

}
