package com.dokidoki.auction.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class SimpleAuctionIngInfo {
    private final Long auction_id;

    private final String auction_title;
    private final String product_name;
    private final String category_name;

    private final Integer offer_price;
    private final Integer cur_price;

    private final Long remain_hours;
    private final Long remain_minutes;
    private final Long remain_seconds;

    private List<String> auction_image_urls;

    public SimpleAuctionIngInfo(
            SimpleAuctionIngInterface simpleAuctionIngInterface,
            List<String> auction_image_urls) {
        this.auction_id = simpleAuctionIngInterface.getAuction_id();
        this.auction_title = simpleAuctionIngInterface.getAuction_title();
        this.product_name = simpleAuctionIngInterface.getProduct_name();
        this.category_name = simpleAuctionIngInterface.getCategory_name();
        this.offer_price = simpleAuctionIngInterface.getOffer_price();
        this.cur_price = simpleAuctionIngInterface.getCur_price();

        this.auction_image_urls = auction_image_urls;

        // 남은 시간 계산
        Long seconds = ChronoUnit.SECONDS.between(
                LocalDateTime.now(), simpleAuctionIngInterface.getEnd_time()
        );

        this.remain_hours = seconds / 3600; seconds %= 3600;
        this.remain_minutes = seconds / 60; seconds %= 60;
        this.remain_seconds = seconds;
    }
}