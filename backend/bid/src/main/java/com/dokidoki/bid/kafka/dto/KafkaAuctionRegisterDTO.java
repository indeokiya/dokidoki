package com.dokidoki.bid.kafka.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
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


}