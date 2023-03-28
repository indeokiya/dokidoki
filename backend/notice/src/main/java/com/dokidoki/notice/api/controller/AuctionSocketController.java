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

    @MessageMapping("ws/auctions/{auctionId}/bid") // kafka 가 이쪽으로 메시지 보내면 함수가 호출됨
    public void getBid(@DestinationVariable long auctionId, @Payload SocketBidInfoResp resp) {
        log.info("sending socketBidInfoResp: {}", resp);

        // 최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감
        simpMessagingTemplate.convertAndSend("/topic/auctions/"+auctionId+"/realtime", resp);

    }

    @MessageMapping("ws/auctions/{auctionId}/price-size")
    public void getPriceSize(@DestinationVariable long auctionId, @Payload SocketPriceSizeResp resp) {

        log.info("sending socketPriceSizeResp: {}", resp);
        // 최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감
        simpMessagingTemplate.convertAndSend("/topic/auctions/"+auctionId+"/realtime", resp);
    }
}
