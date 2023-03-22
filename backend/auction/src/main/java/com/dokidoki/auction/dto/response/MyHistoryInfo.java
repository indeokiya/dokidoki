package com.dokidoki.auction.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyHistoryInfo {
    private final Long auction_id;

    private final String buyer_name;
    private final String seller_name;

    private final Integer offer_price;
    private final Integer final_price;

    private final String category_name;
    private final String product_name;

    private final Integer year, month, day;

    public MyHistoryInfo(MyHistoryInfoInterface myHistoryInfoInterface) {
        this.auction_id = myHistoryInfoInterface.getAuction_id();
        this.buyer_name = myHistoryInfoInterface.getBuyer_name();
        this.seller_name = myHistoryInfoInterface.getSeller_name();
        this.offer_price = myHistoryInfoInterface.getOffer_price();
        this.final_price = myHistoryInfoInterface.getFinal_price();
        this.category_name = myHistoryInfoInterface.getCategory_name();
        this.product_name = myHistoryInfoInterface.getProduct_name();

        LocalDateTime endDate = myHistoryInfoInterface.getEnd_time();
        this.year = endDate.getYear();
        this.month = endDate.getMonthValue();
        this.day = endDate.getDayOfMonth();
    }
}
