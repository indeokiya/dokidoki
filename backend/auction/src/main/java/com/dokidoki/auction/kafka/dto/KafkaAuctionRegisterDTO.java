package com.dokidoki.auction.kafka.dto;

import com.dokidoki.auction.dto.request.AuctionRegisterReq;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaAuctionRegisterDTO {

    private long productId;

    private int offerPrice;             // 시작 가격(호가)

    private int priceSize;              // 경매 단위

    private int highestPrice;

    public KafkaAuctionRegisterDTO() {}

    @Builder
    public KafkaAuctionRegisterDTO(long productId, int offerPrice, int priceSize, int highestPrice) {
        this.productId = productId;
        this.offerPrice = offerPrice;
        this.priceSize = priceSize;
        this.highestPrice = highestPrice;
    }

    public KafkaAuctionRegisterDTO(AuctionRegisterReq auction) {
        this.productId = auction.getProductId();
        this.offerPrice = auction.getOfferPrice();
        this.priceSize = auction.getPriceSize();
        this.highestPrice = this.offerPrice;
    }
}