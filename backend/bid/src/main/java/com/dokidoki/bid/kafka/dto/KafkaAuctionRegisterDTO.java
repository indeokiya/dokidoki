package com.dokidoki.bid.kafka.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaAuctionRegisterDTO {
    
    // TODO - TTL 관련 정보도 넘어와야 함 (종료 시점이든 사이 시간이든 가져오면 가공하면 됨)

    private long productId;

    private int offerPrice;             // 시작 가격(호가)

    private int priceSize;              // 경매 단위

    private int highestPrice;

    private long ttl;

    public KafkaAuctionRegisterDTO() {}

    @Builder
    public KafkaAuctionRegisterDTO(long productId, int offerPrice, int priceSize, int highestPrice) {
        this.productId = productId;
        this.offerPrice = offerPrice;
        this.priceSize = priceSize;
        this.highestPrice = highestPrice;
    }

}