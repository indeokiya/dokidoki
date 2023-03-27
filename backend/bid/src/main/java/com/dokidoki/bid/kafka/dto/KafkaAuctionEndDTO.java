package com.dokidoki.bid.kafka.dto;


import com.dokidoki.bid.db.entity.AuctionRealtime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaAuctionEndDTO {

    private long auctionId;
    private long sellerId;
    private int finalPrice;
    private int priceSize;
    private long productId;
    private String productName;

    public static KafkaAuctionEndDTO of(AuctionRealtime auctionRealtime) {
        KafkaAuctionEndDTO dto = KafkaAuctionEndDTO.builder()
                .auctionId(auctionRealtime.getAuctionId())
                .sellerId(auctionRealtime.getSellerId())
                .finalPrice(auctionRealtime.getHighestPrice())
                .priceSize(auctionRealtime.getPriceSize())
                .productId(auctionRealtime.getProductId())
                .productName(auctionRealtime.getProductName())
                .build();
        return dto;
    }

    public KafkaAuctionEndDTO() {}

    @Builder
    public KafkaAuctionEndDTO(long auctionId, long sellerId, int finalPrice, int priceSize, long productId, String productName) {
        this.auctionId = auctionId;
        this.sellerId = sellerId;
        this.finalPrice = finalPrice;
        this.priceSize = priceSize;
        this.productId = productId;
        this.productName = productName;
    }
}
