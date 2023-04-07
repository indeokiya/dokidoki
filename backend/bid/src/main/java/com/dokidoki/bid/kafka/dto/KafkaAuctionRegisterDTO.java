package com.dokidoki.bid.kafka.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class KafkaAuctionRegisterDTO {

    private Long auctionId;
    private Long offerPrice;             // 시작 가격(호가)
    private Long priceSize;              // 경매 단위
    private Long ttl;
    private Long sellerId;
    private Long productId;
    private String productName;

    public KafkaAuctionRegisterDTO() {}

    public KafkaAuctionRegisterDTO(Long auctionId, Long offerPrice, Long priceSize, Long ttl, Long sellerId, Long productId, String productName) {
        this.auctionId = auctionId;
        this.offerPrice = offerPrice;
        this.priceSize = priceSize;
        this.ttl = ttl;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productName = productName;
    }
}