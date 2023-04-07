package com.dokidoki.notice.kafka.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@ToString
public class KafkaAuctionEndDTO {
    private Long auctionId;
    private Long sellerId;
    private Long buyerId;
    private Long finalPrice;
    private Long priceSize;
    private Long productId;
    private String productName;
    private LocalDateTime endTime; // 끝나는 시점에 생성

    public KafkaAuctionEndDTO() {}

    @Builder
    public KafkaAuctionEndDTO(Long auctionId, Long sellerId, Long buyerId, Long finalPrice, Long priceSize, Long productId, String productName, LocalDateTime endTime) {
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.finalPrice = finalPrice;
        this.priceSize = priceSize;
        this.productId = productId;
        this.productName = productName;
        this.endTime = endTime;
    }

}
