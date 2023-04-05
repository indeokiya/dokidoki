package com.dokidoki.bid.api.service;

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

    @Test
    public void 저장() {
        realtimeInterestService.save("bid 64 1");
        realtimeInterestService.save("bid 65 15");
        realtimeInterestService.save("bid 66 2");
        realtimeInterestService.save("bid 67 3");
        realtimeInterestService.save("click 50 2");
        realtimeInterestService.save("click 51 7");
        realtimeInterestService.save("click 52 10");
        realtimeInterestService.save("click 53 1");
        System.out.println(realtimeInterestService.getRealtimeInterestRanking());
    }

}