package com.dokidoki.bid.api.controller;

import com.dokidoki.bid.api.dto.RealtimeSocketDTO;
import com.dokidoki.bid.api.response.LeaderBoardMemberResp;
import com.dokidoki.bid.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.bid.kafka.dto.KafkaBidDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuctionSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("ws/auctions/{auctionId}/bid") // kafka 가 이쪽으로 메시지 보내면 함수가 호출됨
    public void getBid(@DestinationVariable long auctionId, @Payload KafkaBidDTO kafkaBidDTO) {
        System.out.println(kafkaBidDTO);
        log.info("KafkaDTO:{}",kafkaBidDTO);
        // 최고 가격,

        // Kafka DTO 가 바뀌면 from 메서드도 바꾸기!!
        RealtimeSocketDTO dto = RealtimeSocketDTO.from(kafkaBidDTO);

        // 최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감
        simpMessagingTemplate.convertAndSend("/topic/auctions/"+auctionId+"/realtime", dto);

    }

    @MessageMapping("ws/auctions/{auctionId}/price-size")
    public void getPriceSize(@DestinationVariable long auctionId, @Payload KafkaAuctionUpdateDTO kafkaAuctionUpdateDTO) {
        System.out.println(kafkaAuctionUpdateDTO);
        log.info("KafkaUpdateDTO:{}",kafkaAuctionUpdateDTO);
        // Kafka DTO 가 바뀌면 from 메서드도 바꾸기!!
        RealtimeSocketDTO dto = RealtimeSocketDTO.from(kafkaAuctionUpdateDTO);

        // 최종적으로 client 가 구독해 놓고 데이터를 받아야 하는 링크가 destination 에 들어감
        simpMessagingTemplate.convertAndSend("/topic/auctions/"+auctionId+"/realtime", dto);
    }
}
