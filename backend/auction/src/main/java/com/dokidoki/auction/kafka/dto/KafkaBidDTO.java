package com.dokidoki.auction.kafka.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class KafkaBidDTO {

    private Long beforeWinnerId;
    private Long memberId;
    private Long auctionId;
    private String name;
    private Long highestPrice;
    private String productName;
    private Long productId;
    private LocalDateTime bidTime;
    public KafkaBidDTO() {}

    public KafkaBidDTO(Long beforeWinnerId, Long memberId, Long auctionId, String name, Long highestPrice, String productName, Long productId, LocalDateTime bidTime) {
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
