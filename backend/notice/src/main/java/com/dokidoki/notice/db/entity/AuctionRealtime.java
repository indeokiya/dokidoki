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

    private long auctionId;
    private int highestPrice;
    private int priceSize;
    private long sellerId;

    private long productId;
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
    public AuctionRealtime(long auctionId, int highestPrice, int priceSize, long sellerId, long productId, String productName) {
        this.auctionId = auctionId;
        this.highestPrice = highestPrice;
        this.priceSize = priceSize;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productName = productName;
    }

    public void updatePriceSize(int priceSize) {
        this.priceSize = priceSize;
    }

    public int updateHighestPrice() {
        this.highestPrice = this.highestPrice + this.priceSize;
        return this.highestPrice;
    }

}
