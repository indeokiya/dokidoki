package com.dokidoki.auction.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuctionIngInfoResp {

    private String categoryName;    // 카테고리

    private String productName;     // 제품명

    private String sellerName;      // 판매자

    private String title;           // 제목

    private String description;     // 설명

    private Integer offerPrice;         // 시작가격

    private Integer priceSize;          // 경매 단위

    private LocalDateTime endAt;    // 경매 종료 시점

    // 현재 가격은 실시간 서버에서.

}
