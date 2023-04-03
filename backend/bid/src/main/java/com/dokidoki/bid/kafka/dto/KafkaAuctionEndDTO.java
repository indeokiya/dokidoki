package com.dokidoki.bid.kafka.dto;


import com.dokidoki.bid.api.response.LeaderBoardMemberInfo;
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

    private Long auctionId;
    private Long sellerId;
    private Long buyerId;
    private Long finalPrice;
    private Long priceSize;
    private Long productId;
    private String productName;
    private LocalDateTime endTime; // 끝나는 시점에 생성

    public static KafkaAuctionEndDTO of(AuctionRealtime auctionRealtime, LeaderBoardMemberInfo winner) {
        KafkaAuctionEndDTO dto = KafkaAuctionEndDTO.builder()
                .auctionId(auctionRealtime.getAuctionId())
                .sellerId(auctionRealtime.getSellerId())
                .buyerId(winner.getMemberId())
                .finalPrice(auctionRealtime.getHighestPrice())
                .priceSize(auctionRealtime.getPriceSize())
                .productId(auctionRealtime.getProductId())
                .productName(auctionRealtime.getProductName())
                .endTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
        return dto;
    }
    public static KafkaAuctionEndDTO of(AuctionRealtime auctionRealtime, Long buyerId) {
        KafkaAuctionEndDTO dto = KafkaAuctionEndDTO.builder()
                .auctionId(auctionRealtime.getAuctionId())
                .sellerId(auctionRealtime.getSellerId())
                .buyerId(buyerId)
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
    public KafkaAuctionEndDTO(Long auctionId, Long sellerId, Long buyerId, Long finalPrice, Long priceSize, Long productId, String productName, LocalDateTime endTime) {
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.finalPrice = finalPrice;
        this.priceSize = priceSize;
        this.productId = productId;
        this.productName = productName;
        this.endTime = endTime;
    }
}
