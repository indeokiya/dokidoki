package com.dokidoki.notice.kafka.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class KafkaAuctionRegisterDTO {

    private long auctionId;
    private int offerPrice;             // 시작 가격(호가)
    private int priceSize;              // 경매 단위
    private long ttl;
    private long sellerId;
    private long productId;
    private String productName;

    public KafkaAuctionRegisterDTO() {}

    public KafkaAuctionRegisterDTO(long auctionId, int offerPrice, int priceSize, long ttl, long sellerId, long productId, String productName) {
        this.auctionId = auctionId;
        this.offerPrice = offerPrice;
        this.priceSize = priceSize;
        this.ttl = ttl;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productName = productName;
    }
}