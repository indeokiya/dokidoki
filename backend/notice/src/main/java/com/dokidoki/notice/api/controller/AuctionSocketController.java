package com.dokidoki.notice.api.controller;

import com.dokidoki.notice.api.response.SocketBidInfoResp;
import com.dokidoki.notice.api.response.SocketPriceSizeResp;
import com.dokidoki.notice.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuctionSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

//    @MessageMapping("/ws/auctions/{auctionId}/bid") // kafka 가 이쪽으로 메시지 보내면 함수가 호출됨
    public void getBid(long auctionId, String payload) {
        log.info("sending socketBidInfoResp: {}", payload);

        // 최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감
        simpMessagingTemplate.convertAndSend("/topic/auctions/"+auctionId+"/realtime", payload);

    }

//    @MessageMapping("/ws/auctions/{auctionId}/price-size")
//    public void getPriceSize(@DestinationVariable long auctionId, @Payload String payload) {
    public void getPriceSize(long auctionId, String payload) {
        log.info("sending socketPriceSizeResp: {}", payload);
        // 최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감
        simpMessagingTemplate.convertAndSend("/topic/auctions/"+auctionId+"/realtime", payload);
    }

    @MessageMapping("/")
    public void test(@Payload String payload) {
        System.out.println(payload);
    }
}
