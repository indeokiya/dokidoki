package com.dokidoki.bid.api.service;

import com.dokidoki.bid.db.entity.AuctionRealtime;
import com.dokidoki.bid.db.repository.AuctionRealtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RealtimeInterestService 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RealtimeInterestServiceTest {

    @Autowired public RealtimeInterestService realtimeInterestService;
    @Autowired public AuctionRealtimeRepository auctionRealtimeRepository;


    AuctionRealtime auctionRealtime1 = AuctionRealtime.builder()
            .auctionId(3L).highestPrice(3000L).build();
    AuctionRealtime auctionRealtime2 = AuctionRealtime.builder()
            .auctionId(4L).highestPrice(3000L).build();
    AuctionRealtime auctionRealtime3 = AuctionRealtime.builder()
            .auctionId(5L).highestPrice(3000L).build();
    AuctionRealtime auctionRealtime4 = AuctionRealtime.builder()
            .auctionId(6L).highestPrice(3000L).build();
    AuctionRealtime auctionRealtime5 = AuctionRealtime.builder()
            .auctionId(11L).highestPrice(3000L).build();
    AuctionRealtime auctionRealtime6 = AuctionRealtime.builder()
            .auctionId(13L).highestPrice(3000L).build();
    AuctionRealtime auctionRealtime7 = AuctionRealtime.builder()
            .auctionId(14L).highestPrice(3000L).build();
    AuctionRealtime auctionRealtime8 = AuctionRealtime.builder()
            .auctionId(18L).highestPrice(3000L).build();

    @BeforeEach
    public void 준비() {
        auctionRealtimeRepository.save(auctionRealtime1);
        auctionRealtimeRepository.save(auctionRealtime2);
        auctionRealtimeRepository.save(auctionRealtime3);
        auctionRealtimeRepository.save(auctionRealtime4);
        auctionRealtimeRepository.save(auctionRealtime5);
        auctionRealtimeRepository.save(auctionRealtime6);
        auctionRealtimeRepository.save(auctionRealtime7);
        auctionRealtimeRepository.save(auctionRealtime8);

    }

    @Test
    public void 저장() {
        realtimeInterestService.save("bid 3 1");
        realtimeInterestService.save("bid 4 15");
        realtimeInterestService.save("bid 5 2");
        realtimeInterestService.save("bid 6 3");
        realtimeInterestService.save("click 11 2");
        realtimeInterestService.save("click 13 7");
        realtimeInterestService.save("click 14 10");
        realtimeInterestService.save("click 18 1");
        System.out.println(realtimeInterestService.getRealtimeInterestRanking());
    }

}