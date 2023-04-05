package com.dokidoki.bid.common.utils;

import com.dokidoki.bid.api.response.DetailAuctionIngData;
import com.dokidoki.bid.api.response.DetailAuctionIngResp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayName("HttpUtil 클래스")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class HttpUtilTest {

    @Autowired
    private HttpUtil httpUtil;

    @Test
    public void 응답() {
        DetailAuctionIngResp auctionInfo = httpUtil.getAuctionInfo(29L);
        System.out.println(auctionInfo);


    }

}