package com.dokidoki.auction.kafka.service;

import com.dokidoki.auction.kafka.dto.KafkaAuctionEndDTO;
import com.dokidoki.auction.kafka.dto.KafkaBidDTO;
import com.dokidoki.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaAuctionConsumer {
    private final AuctionService auctionService;

    @KafkaListener(topics = "${spring.kafka.auctionEndConfig.topic}", containerFactory = "auctionEndKafkaListenerContainerFactory")
    public void auctionEndListener(
            @Payload KafkaAuctionEndDTO auction,
            @Headers MessageHeaders headers) {
        System.out.println("Received auction end message : " + auction);
        log.info("Received auction end message: [{}]", auction);
        headers.keySet().forEach(key -> {
            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
        });


        // auction : memberid, name, email .....
        // websocket method_bid_success(memberid, name, email)
        // 경매 종료 이벤트
        auctionService.auctionEndEvent(auction.getAuctionId(), auction.getBuyerId());
    }

    @KafkaListener(topics = "${spring.kafka.bidConfig.topic}", containerFactory = "bidKafkaListenerContainerFactory")
    public void bidListener(
            @Payload KafkaBidDTO bid,
            @Headers MessageHeaders headers) {
        System.out.println("Received bid message : " + bid);
        log.info("Received bid message: [{}]", bid);
        headers.keySet().forEach(key -> {
            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
        });
        // 최고가 갱신
        auctionService.updateHighestPrice(bid.getAuctionId(), bid.getHighestPrice());
    }
}
