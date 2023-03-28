package com.dokidoki.auction.kafka.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class KafkaBidDTO {

    private long beforeWinnerId;
    private long memberId;
    private long auctionId;
    private String name;
    private int highestPrice;
    private String productName;
    private long productId;
    private LocalDateTime bidTime;
    public KafkaBidDTO() {}

    public KafkaBidDTO(long beforeWinnerId, long memberId, long auctionId, String name, int highestPrice, String productName, long productId, LocalDateTime bidTime) {
        this.beforeWinnerId = beforeWinnerId;
        this.memberId = memberId;
        this.auctionId = auctionId;
        this.name = name;
        this.highestPrice = highestPrice;
        this.productName = productName;
        this.productId = productId;
        this.bidTime = bidTime;
    }
}
