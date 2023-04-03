package com.dokidoki.notice.kafka.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaAuctionUpdateDTO {

    private Long auctionId;
    private Long priceSize;              // 경매 단위

    public KafkaAuctionUpdateDTO() {}

    @Builder
    public KafkaAuctionUpdateDTO(Long auctionId, Long priceSize) {
        this.auctionId = auctionId;
        this.priceSize = priceSize;
    }
}
