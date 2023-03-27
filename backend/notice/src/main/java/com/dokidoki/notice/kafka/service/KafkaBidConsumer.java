package com.dokidoki.notice.kafka.service;

import com.dokidoki.notice.kafka.dto.KafkaAuctionRegisterDTO;
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
        System.out.print("header | ");
        headers.keySet().forEach(key -> {
//            log.debug("header | key: [{}] value: [{}]", key, headers.get(key));
            System.out.print("{" + key + ", " + headers.get(key) + "} ");
        });
        System.out.println("");

        System.out.println();

        biddingService.registerAuctionInfo(dto);

        // auction : memberid, name, email .....
        // websocket method_bid_success(memberid, name, email)

    }
//
//    @KafkaListener(topics = "${spring.kafka.auctionUpdateConfig.topic}", containerFactory = "auctionUpdateKafkaListenerContainerFactory")
//    public void auctionUpdateListener(
//            @Payload KafkaAuctionUpdateDTO update,
//            @Headers MessageHeaders headers) {
//        System.out.println("Received auction update message : " + update);
//        System.out.print("header | ");
//        headers.keySet().forEach(key -> {
////            log.debug("header | key: [{}] value: [{}]", key, headers.get(key));
//            System.out.print("{" + key + ", " + headers.get(key) + "} ");
//        });
//        System.out.println("");
//    }
}
