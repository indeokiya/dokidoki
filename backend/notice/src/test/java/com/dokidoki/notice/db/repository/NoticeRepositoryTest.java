package com.dokidoki.notice.db.repository;

import com.dokidoki.notice.api.response.NoticeFailResp;
import com.dokidoki.notice.api.response.NoticeResp;
import com.dokidoki.notice.api.response.NoticeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NoticeRepository 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class NoticeRepositoryTest {

    @Autowired
    NoticeRepository noticeRepository;

    static long auctionId = 1;
    static long memberId = 30;
    static long productId = 2;
    static int finalPrice = 500;
    static int myFinalPrice = 400;
    static String productName = "갤럭시 노트 23";

    NoticeFailResp resp = NoticeFailResp.builder()
            .type(NoticeType.PURCHASE_FAIL)
            .auctionId(auctionId)
            .productId(productId)
            .finalPrice(finalPrice)
            .myFinalPrice(myFinalPrice)
            .productName(productName)
            .timeStamp(LocalDateTime.now())
            .build();

    @Test
    public void 테스트() {

        noticeRepository.save(memberId, resp);
        System.out.println(noticeRepository.getAll(memberId));

    }

}