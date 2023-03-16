package com.dokidoki.auction.kafka.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class KafkaBidDTO {

    private long auctionId;
    private long memberId;
    int status; // -1: 이미 종료된 경매 0: 입찰 실패 1: 입찰 성공
    private String name;
    private String email;
    private int price;
    private LocalDateTime time;

    public KafkaBidDTO() {}

    @Builder
    public KafkaBidDTO(long auctionId, long memberId, int status, String name, String email, int price, LocalDateTime time) {
        this.auctionId = auctionId;
        this.memberId = memberId;
        this.status = status;
        this.name = name;
        this.email = email;
        this.price = price;
        this.time = time;
    }
}
