package com.dokidoki.bid.db.entity;

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
