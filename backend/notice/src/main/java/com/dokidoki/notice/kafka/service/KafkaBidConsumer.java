//package com.dokidoki.notice.kafka.service;
//
//import com.dokidoki.notice.api.service.NoticeService;
//import com.dokidoki.notice.kafka.dto.KafkaAuctionEndDTO;
//import com.dokidoki.notice.kafka.dto.KafkaAuctionRegisterDTO;
//import com.dokidoki.notice.kafka.dto.KafkaAuctionUpdateDTO;
//import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.messaging.handler.annotation.Headers;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class KafkaBidConsumer {
//
//    @Autowired
//    NoticeService noticeService;
//
//    @KafkaListener(topics = "${spring.kafka.auctionUpdateConfig.topic}", containerFactory = "auctionUpdateKafkaListenerContainerFactory")
//    public void auctionUpdateListener(
//            @Payload KafkaAuctionUpdateDTO dto,
//            @Headers MessageHeaders headers) {
//        System.out.println("Received auction update message : " + dto);
//        log.info("Received auction update message: [{}]", dto);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });
//
//        // auction : memberid, name, email .....
//        // websocket method_bid_success(memberid, name, email)
//    }
//
//    @KafkaListener(topics = "${spring.kafka.auctionEndConfig.topic}", containerFactory = "auctionEndKafkaListenerContainerFactory")
//    public void auctionEndListener(
//            @Payload KafkaAuctionEndDTO dto,
//            @Headers MessageHeaders headers) {
//        System.out.println("Received auction end message : " + dto);
//        log.info("Received auction end message: [{}]", dto);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });
//
//        noticeService.auctionComplete(dto);
//        noticeService.auctionSuccess(dto);
//        noticeService.auctionFail(dto);
//    }
//
//    @KafkaListener(topics = "${spring.kafka.bidConfig.topic}", containerFactory = "bidKafkaListenerContainerFactory")
//    public void bidListener(
//            @Payload KafkaBidDTO dto,
//            @Headers MessageHeaders headers) {
//        System.out.println("Received auction end message : " + dto);
//        log.info("Received auction end message: [{}]", dto);
//        headers.keySet().forEach(key -> {
//            log.info("header | key: [{}] value: [{}]", key, headers.get(key));
//        });
//
//        noticeService.auctionOutBid(dto);
//    }
//}
