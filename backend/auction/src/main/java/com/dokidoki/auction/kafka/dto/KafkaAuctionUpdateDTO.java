package com.dokidoki.auction.kafka.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaAuctionUpdateDTO {
    private long productId;
    private int priceSize;              // 경매 단위

    public KafkaAuctionUpdateDTO() {}
    @Builder
    public KafkaAuctionUpdateDTO(long productId, int priceSize) {
        this.productId = productId;
        this.priceSize = priceSize;
    }
}
