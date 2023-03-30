package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.AuctionEndEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyHistoryInfo {
    private final Long auction_id;

    private final String auction_title;

    private final String buyer_name;
    private final String seller_name;

    private final Integer offer_price;
    private final Integer final_price;

    private final String category_name;
    private final String product_name;

    private final String auction_image_url;

    private final Integer year, month, day;

    public MyHistoryInfo(AuctionEndEntity auctionEndEntity, String auctionImageUrl) {
        this.auction_id = auctionEndEntity.getId();
        this.auction_title = auctionEndEntity.getTitle();
        this.buyer_name = auctionEndEntity.getBuyer().getName();
        this.seller_name = auctionEndEntity.getSeller().getName();
        this.offer_price = auctionEndEntity.getOfferPrice();
        this.final_price = auctionEndEntity.getFinalPrice();
        this.category_name = auctionEndEntity.getProduct().getCategoryEntity().getCategoryName();
        this.product_name = auctionEndEntity.getProduct().getName();
        this.auction_image_url = auctionImageUrl;
        LocalDateTime endDate = auctionEndEntity.getEndTime();
        this.year = endDate.getYear();
        this.month = endDate.getMonthValue();
        this.day = endDate.getDayOfMonth();
    }
}
