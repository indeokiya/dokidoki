package com.dokidoki.notice.api.service;

import com.dokidoki.notice.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuctionSocketService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuctionSocketServiceTest {

    @Autowired
    private AuctionSocketService auctionSocketService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    long auctionId = 10_000;
    long memberId = 20_000;
    String name = "임아무개";
    int highestPrice = 700_000;
    String productName = "갤럭시 노트 20";
    long productId = 30_000;
    int priceSize = 500;
    LocalDateTime bidTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    KafkaBidDTO kafkaBidDTO = KafkaBidDTO.builder()
            .auctionId(auctionId)
            .memberId(memberId)
            .name(name)
            .highestPrice(highestPrice)
            .productName(productName)
            .productId(productId)
            .bidTime(bidTime).build();

    KafkaAuctionUpdateDTO kafkaAuctionUpdateDTO = KafkaAuctionUpdateDTO.builder()
            .auctionId(auctionId)
            .priceSize(priceSize).build();

    @Test
    public void auctionBid_전달_테스트() {
        auctionSocketService.auctionBid(kafkaBidDTO);
    }

    @Test
    public void auctionUpdate_전달_테스트() {
        auctionSocketService.auctionUpdate(kafkaAuctionUpdateDTO);
    }

    @Test
    public void 테스트() throws InterruptedException {
        Thread.sleep(4000);
        simpMessagingTemplate.convertAndSend("/", "테스트");
        Thread.sleep(4000);
    }


}