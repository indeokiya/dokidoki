package com.dokidoki.auction.kafka.dto;

import com.dokidoki.auction.dto.request.AuctionRegisterReq;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class KafkaAuctionRegisterDTO {

    private long auctionId;
    private int offerPrice;             // 시작 가격(호가)
    private int priceSize;              // 경매 단위
    private long ttl;
    private long sellerId;

    private long productId;
    private String productName;

    public KafkaAuctionRegisterDTO() {}



    public KafkaAuctionRegisterDTO(AuctionRegisterReq auction, long auctionId, long ttl, long sellerId, long productId, String productName) {
        this.ttl = ttl;
        this.offerPrice = auction.getOffer_price();
        this.priceSize = auction.getPrice_size();
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productName = productName;
    }
}