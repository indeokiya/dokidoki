package com.dokidoki.bid.db.entity;

import com.dokidoki.bid.kafka.dto.KafkaAuctionRegisterDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

@Getter
@ToString
@NoArgsConstructor
public class AuctionRealtime {

    private long auctionId;
    private int highestPrice;
    private int priceSize;

    public static AuctionRealtime from(KafkaAuctionRegisterDTO dto) {
        AuctionRealtime auctionRealtime = AuctionRealtime.builder()
                .auctionId(dto.getProductId())
                .highestPrice(dto.getHighestPrice())
                .priceSize(dto.getPriceSize())
                .build();
        return auctionRealtime;
    }

    @Builder
    public AuctionRealtime(long auctionId, int highestPrice, int priceSize) {
        this.auctionId = auctionId;
        this.highestPrice = highestPrice;
        this.priceSize = priceSize;
    }

    public void updatePriceSize(int priceSize) {
        this.priceSize = priceSize;
    }

    public int updateHighestPrice() {
        this.highestPrice = this.highestPrice + this.priceSize;
        return this.highestPrice;
    }

}
