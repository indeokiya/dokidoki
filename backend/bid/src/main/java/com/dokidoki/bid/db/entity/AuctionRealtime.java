//package com.dokidoki.bid.db.entity;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.ToString;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.redis.core.RedisHash;
//import org.springframework.data.redis.core.TimeToLive;
//import org.springframework.data.redis.core.index.Indexed;
//
//@Getter
//@RedisHash(value = "auction")
//@ToString
//public class AuctionRealtime {
//
//    @Id @Indexed
//    private long auctionId;
//    private int highestPrice;
//    private int priceSize;
//
//    @TimeToLive
//    private long lifeSpan; // 초 단위
//
//    @Builder
//    public AuctionRealtime(long auctionId, Integer highestPrice, Integer priceSize, long lifeSpan) {
//        this.auctionId = auctionId;
//        this.highestPrice = highestPrice;
//        this.priceSize = priceSize;
//        this.lifeSpan = lifeSpan;
//    }
//
//    public void updatePriceSize(int priceSize) {
//        this.priceSize = priceSize;
//    }
//
//    public int updateHighestPrice() {
//        this.highestPrice = this.highestPrice + this.priceSize;
//        return this.highestPrice;
//    }
//
//
//}
