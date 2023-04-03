package com.dokidoki.bid.kafka.dto;

import com.dokidoki.bid.db.entity.AuctionRealtime;
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

    public static KafkaAuctionUpdateDTO of(AuctionRealtime auctionRealtime) {
        KafkaAuctionUpdateDTO dto = KafkaAuctionUpdateDTO.builder()
                .auctionId(auctionRealtime.getAuctionId())
                .priceSize(auctionRealtime.getPriceSize())
                .build();
        return dto;
    }

    @Builder
    public KafkaAuctionUpdateDTO(Long auctionId, Long priceSize) {
        this.auctionId = auctionId;
        this.priceSize = priceSize;
    }
}
