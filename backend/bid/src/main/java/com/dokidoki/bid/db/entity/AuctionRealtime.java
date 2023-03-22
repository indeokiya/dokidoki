package com.dokidoki.bid.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

@REntity
@Getter
@ToString
@NoArgsConstructor
public class AuctionRealtime {

    // null로 값이 넘어올 수 있기 때문에, Wrapper class 로 field 정의하기..
    // 생성자로 바로 field값 주입하면 안됨 (getter, setter 만 번역됨)
    @RId
    public Long auctionId;
    public Integer highestPrice;
    public Integer priceSize;

    public void updatePriceSize(Integer priceSize) {
        this.priceSize = priceSize;
    }

    public Integer updateHighestPrice() {
        this.highestPrice = this.highestPrice + this.priceSize;
        return this.highestPrice;
    }

    public static AuctionRealtime of(Long auctionId, Integer highestPrice, Integer priceSize) {
        AuctionRealtime auctionRealtime = new AuctionRealtime();
        auctionRealtime.setAuctionId(auctionId);
        auctionRealtime.setHighestPrice(highestPrice);
        auctionRealtime.setPriceSize(priceSize);
        return auctionRealtime;
    }


    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }


    public void setHighestPrice(Integer highestPrice) {
        this.highestPrice = highestPrice;
    }
    
    // public 으로 열려있어야 작동함
    public void setPriceSize(Integer priceSize) {
        this.priceSize = priceSize;
    }
}
