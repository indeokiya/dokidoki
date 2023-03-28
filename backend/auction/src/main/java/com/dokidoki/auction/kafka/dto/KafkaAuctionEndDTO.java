package com.dokidoki.auction.kafka.dto;


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
    private int finalPrice;
    private int priceSize;
    private long productId;
    private String productName;

    public KafkaAuctionEndDTO() {}
    @Builder
    public KafkaAuctionEndDTO(long auctionId, long sellerId, int finalPrice, int priceSize, long productId, String productName) {
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.finalPrice = finalPrice;
        this.priceSize = priceSize;
        this.productId = productId;
        this.productName = productName;
    }
}
