package com.dokidoki.auction.dto.response;

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

    private List<String> auction_image_urls;

    private Boolean is_sold_out;

    public SimpleAuctionEndInfo(
            SimpleAuctionEndInterface simpleAuctionEndInterface,
            List<String> auction_image_urls) {
        this.auction_id = simpleAuctionEndInterface.getAuction_id();
        this.auction_title = simpleAuctionEndInterface.getAuction_title();
        this.product_name = simpleAuctionEndInterface.getProduct_name();
        this.category_name = simpleAuctionEndInterface.getCategory_name();
        this.offer_price = simpleAuctionEndInterface.getOffer_price();
        this.final_price = simpleAuctionEndInterface.getFinal_price();
        this.start_time = simpleAuctionEndInterface.getStart_time();
        this.end_time = simpleAuctionEndInterface.getEnd_time();

        this.auction_image_urls = auction_image_urls;
        this.is_sold_out = simpleAuctionEndInterface.getBuyer_id() != null;
    }
}
