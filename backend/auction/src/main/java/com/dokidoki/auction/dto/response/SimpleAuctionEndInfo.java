package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.domain.entity.AuctionEndEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class SimpleAuctionEndInfo {
    private final Long auction_id;

    private final String auction_title;
    private final String product_name;
    private final String category_name;

    private final Integer offer_price;
    private final Integer final_price;

    private final LocalDateTime start_time;
    private final LocalDateTime end_time;

    private String auction_image_url;

    private Boolean is_sold_out;

    public SimpleAuctionEndInfo(
            AuctionEndEntity auctionEndEntity,
            String auction_image_url) {
        this.auction_id = auctionEndEntity.getId();
        this.auction_title = auctionEndEntity.getTitle();
        this.product_name = auctionEndEntity.getProduct().getName();
        this.category_name = auctionEndEntity.getProduct().getCategoryEntity().getCategoryName();
        this.offer_price = auctionEndEntity.getOfferPrice();
        this.final_price = auctionEndEntity.getFinalPrice();
        this.start_time = auctionEndEntity.getStartTime();
        this.end_time = auctionEndEntity.getEndTime();

        this.auction_image_url = auction_image_url;
        this.is_sold_out = auctionEndEntity.getBuyer() != null;
    }
}
