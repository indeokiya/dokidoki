package com.dokidoki.bid.kafka.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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