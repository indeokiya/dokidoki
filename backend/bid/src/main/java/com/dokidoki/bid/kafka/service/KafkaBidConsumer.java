package com.dokidoki.bid.kafka.service;

import com.dokidoki.bid.api.service.BiddingService;
import com.dokidoki.bid.kafka.dto.KafkaAuctionRegisterDTO;
import com.dokidoki.bid.kafka.dto.KafkaAuctionUpdateDTO;
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
public class KafkaBidConsumer {

    private final BiddingService biddingService;

    @KafkaListener(topics = "${spring.kafka.auctionRegisterConfig.topic}", containerFactory = "auctionRegisterKafkaListenerContainerFactory")
    public void auctionRegisterListener(
            @Payload KafkaAuctionRegisterDTO dto,
            @Headers MessageHeaders headers) {
        System.out.println("Received auction register message : " + dto);
        log.info("Received auction register message: [{}]", dto);
        headers.keySet().forEach(key -> {
            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
        });

        biddingService.registerAuctionInfo(dto);

        // auction : memberid, name, email .....
        // websocket method_bid_success(memberid, name, email)

    }

    @KafkaListener(topics = "${spring.kafka.auctionUpdateConfig.topic}", containerFactory = "auctionUpdateKafkaListenerContainerFactory")
    public void auctionUpdateListener(
            @Payload KafkaAuctionUpdateDTO dto,
            @Headers MessageHeaders headers) {
        System.out.println("Received auction update message : " + dto);
        log.info("Received auction update message: [{}]", dto);
        headers.keySet().forEach(key -> {
            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
        });

        biddingService.updatePriceSize(dto);
    }
}
