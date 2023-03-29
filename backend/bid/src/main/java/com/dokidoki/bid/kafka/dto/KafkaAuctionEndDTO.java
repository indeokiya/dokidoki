package com.dokidoki.bid.kafka.dto;


import com.dokidoki.bid.db.entity.AuctionRealtime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
    private LocalDateTime endTime; // 끝나는 시점에 생성

    public static KafkaAuctionEndDTO of(AuctionRealtime auctionRealtime) {
        KafkaAuctionEndDTO dto = KafkaAuctionEndDTO.builder()
                .auctionId(auctionRealtime.getAuctionId())
                .sellerId(auctionRealtime.getSellerId())
                .finalPrice(auctionRealtime.getHighestPrice())
                .priceSize(auctionRealtime.getPriceSize())
                .productId(auctionRealtime.getProductId())
                .productName(auctionRealtime.getProductName())
                .endTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
        return dto;
    }

    public KafkaAuctionEndDTO() {}

    @Builder
    public KafkaAuctionEndDTO(long auctionId, long sellerId, int finalPrice, int priceSize, long productId, String productName, LocalDateTime endTime) {
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.finalPrice = finalPrice;
        this.priceSize = priceSize;
        this.productId = productId;
        this.productName = productName;
        this.endTime = endTime;
    }
}
