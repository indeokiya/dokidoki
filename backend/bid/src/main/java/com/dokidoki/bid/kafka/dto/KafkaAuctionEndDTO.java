package com.dokidoki.bid.kafka.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaAuctionEndDTO {

    private long auctionId;
    private long sellerId;
    private long buyerId;
    private int finalPrice;

    public KafkaAuctionEndDTO() {}

    @Builder
    public KafkaAuctionEndDTO(long auctionId, long sellerId, long buyerId, int finalPrice) {
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.finalPrice = finalPrice;
    }
}
