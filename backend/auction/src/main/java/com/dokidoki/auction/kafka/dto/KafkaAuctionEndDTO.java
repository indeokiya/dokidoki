package com.dokidoki.auction.kafka.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class KafkaAuctionEndDTO {
    private long auctionId;
    private long sellerId;
    private long buyerId;
    private int finalPrice;
    private int priceSize;
    private long productId;
    private String productName;
    private LocalDateTime endTime; // 끝나는 시점에 생성

    public KafkaAuctionEndDTO() {}

    @Builder
    public KafkaAuctionEndDTO(long auctionId, long sellerId, long buyerId, int finalPrice, int priceSize, long productId, String productName, LocalDateTime endTime) {
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
