package com.dokidoki.auction.service;

import com.dokidoki.auction.dto.response.ProductResp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class AuctionServiceTest {

    @Autowired
    AuctionService auctionService;


    @DisplayName("카테고리 기준 제품 목록 조회")
    @Test
    public void getProductListTest() {
        // given
        long catId = 1L;

        // when
        List<ProductResp> productRespList = auctionService.getProductList(catId);

        // then
        ProductResp productResp = productRespList.get(0);
        System.out.println(productResp);
        Assertions.assertEquals(productRespList.get(0).getName(), "갤럭시 S23 Ultra 자급제");
        Assertions.assertEquals(productRespList.get(0).getImgUrl(), "https://images.samsung.com/kdp/goods/2023/01/12/592a1b22-ff0a-4fdc-8616-057cdd074e00.png?$PD_GALLERY_L_PNG$");
        Assertions.assertEquals(productRespList.get(0).getSaleCnt(), 0);
    }
}
