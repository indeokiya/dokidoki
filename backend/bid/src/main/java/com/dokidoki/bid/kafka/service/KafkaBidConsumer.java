package com.dokidoki.bid.kafka.service;

import com.dokidoki.bid.kafka.dto.KafkaAuctionRegisterDTO;
import com.dokidoki.bid.kafka.dto.KafkaAuctionUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaBidConsumer {

    @KafkaListener(topics = "${spring.kafka.auctionRegisterConfig.topic}", containerFactory = "auctionRegisterKafkaListenerContainerFactory")
    public void auctionRegisterListener(
            @Payload KafkaAuctionRegisterDTO auction,
            @Headers MessageHeaders headers) {
        System.out.println("Received auction register message : " + auction);
        System.out.print("header | ");
        headers.keySet().forEach(key -> {
//            log.debug("header | key: [{}] value: [{}]", key, headers.get(key));
            System.out.print("{" + key + ", " + headers.get(key) + "} ");
        });
        System.out.println("");

        // auction : memberid, name, email .....
        // websocket method_bid_success(memberid, name, email)

    }

    @KafkaListener(topics = "${spring.kafka.auctionUpdateConfig.topic}", containerFactory = "auctionUpdateKafkaListenerContainerFactory")
    public void auctionUpdateListener(
            @Payload KafkaAuctionUpdateDTO update,
            @Headers MessageHeaders headers) {
        System.out.println("Received auction update message : " + update);
        System.out.print("header | ");
        headers.keySet().forEach(key -> {
//            log.debug("header | key: [{}] value: [{}]", key, headers.get(key));
            System.out.print("{" + key + ", " + headers.get(key) + "} ");
        });
        System.out.println("");
    }
}
