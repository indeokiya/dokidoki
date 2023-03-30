package com.dokidoki.notice.api.service;

import com.dokidoki.notice.db.repository.AuctionRealtimeLeaderBoardRepository;
import com.dokidoki.notice.db.repository.AuctionRealtimeMemberRepository;
import com.dokidoki.notice.db.repository.NoticeRepository;
import com.dokidoki.notice.kafka.dto.KafkaAuctionEndDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NoticeService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class NoticeServiceTest {

    @Autowired NoticeService noticeService;
    @Autowired NoticeRepository noticeRepository;
    @Autowired AuctionRealtimeMemberRepository auctionRealtimeMemberRepository;
    @Autowired AuctionRealtimeLeaderBoardRepository auctionRealtimeLeaderBoardRepository;

    static long sellerId = 10_000;
    static long buyerId = 20_000;
    static long auctionId = 500;
    static long[] bidderIds = { 40_000, 40_001, 40_002, 40_003, 40_004, 40_005 };
    static int finalPrice = 5_000;
    static int priceSize = 100;
    static long productId = 30_000;
    static String productName = "갤럭시 노트 23";
    static LocalDateTime endTime = LocalDateTime.now();


    KafkaAuctionEndDTO kafkaAuctionEndDTO = KafkaAuctionEndDTO
            .builder()
            .auctionId(auctionId)
            .sellerId(sellerId)
            .buyerId(buyerId)
            .finalPrice(finalPrice)
            .priceSize(priceSize)
            .productId(productId)
            .productName(productName)
            .endTime(endTime).build();

    @BeforeEach
    public void 준비() {
        // 1. 삭제
        noticeRepository.deleteAll(sellerId);
        noticeRepository.deleteAll(buyerId);
        for (long id: bidderIds) {
            noticeRepository.deleteAll(id);
        }
        auctionRealtimeMemberRepository.deleteAll(auctionId);
        auctionRealtimeLeaderBoardRepository.deleteAll(auctionId);

        // 2. 등록


    }

    @Test
    public void 경매성공() {
//        noticeService.auctionSuccess(kafkaAuctionEndDTO);
//        System.out.println(noticeRepository.getAll(buyerId));
    }

    @Test
    public void 경매실패() {

    }

    @Test
    public void 판매성공() {

    }

    @Test
    public void 입찰강탈() {

    }



}