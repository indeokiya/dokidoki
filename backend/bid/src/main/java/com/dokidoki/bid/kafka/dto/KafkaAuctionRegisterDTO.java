package com.dokidoki.bid.kafka.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaAuctionRegisterDTO {

    private long productId;

    private int offerPrice;             // 시작 가격(호가)

    private int priceSize;              // 경매 단위

    private int highestPrice;

    public KafkaAuctionRegisterDTO() {}

    @Builder
    public KafkaAuctionRegisterDTO(long productId, int offerPrice, int priceSize, int highestPrice) {
        this.productId = productId;
        this.offerPrice = offerPrice;
        this.priceSize = priceSize;
        this.highestPrice = highestPrice;
    }

}