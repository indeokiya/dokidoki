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
    private Long auctionId;
    private Long offerPrice;             // 시작 가격(호가)
    private Long priceSize;              // 경매 단위
    private Long ttl;
    private Long sellerId;
    private Long productId;
    private String productName;

    public KafkaAuctionRegisterDTO() {}
    public KafkaAuctionRegisterDTO(AuctionRegisterReq auction, Long auctionId, Long ttl, Long sellerId, Long productId, String productName) {
        this.ttl = ttl;
        this.offerPrice = auction.getOffer_price();
        this.priceSize = auction.getPrice_size();
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productName = productName;
    }
}