package com.dokidoki.notice.db.entity;

import com.dokidoki.notice.kafka.dto.KafkaAuctionRegisterDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AuctionRealtime {

    private Long auctionId;
    private Long highestPrice;
    private Long priceSize;
    private Long sellerId;

    private Long productId;
    private String productName;

    public static AuctionRealtime from(KafkaAuctionRegisterDTO dto) {
        AuctionRealtime auctionRealtime = AuctionRealtime.builder()
                .auctionId(dto.getAuctionId())
                .highestPrice(dto.getOfferPrice())
                .priceSize(dto.getPriceSize())
                .sellerId(dto.getSellerId())
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .build();
        return auctionRealtime;
    }

    @Builder
    public AuctionRealtime(Long auctionId, Long highestPrice, Long priceSize, Long sellerId, Long productId, String productName) {
        this.auctionId = auctionId;
        this.highestPrice = highestPrice;
        this.priceSize = priceSize;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productName = productName;
    }

    public void updatePriceSize(Long priceSize) {
        this.priceSize = priceSize;
    }

    public Long updateHighestPrice() {
        this.highestPrice = this.highestPrice + this.priceSize;
        return this.highestPrice;
    }

}
