package com.dokidoki.bid.kafka.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class KafkaBidDTO {

    private long memberId;
    private long auctionId;
    private String name;
    private int highestPrice;
    private LocalDateTime bidTime;
    public KafkaBidDTO() {}

    @Builder
    public KafkaBidDTO(long memberId, long auctionId, String name, int highestPrice, LocalDateTime bidTime) {
        this.memberId = memberId;
        this.auctionId = auctionId;
        this.name = name;
        this.highestPrice = highestPrice;
        this.bidTime = bidTime;
    }
}
