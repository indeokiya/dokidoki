package com.dokidoki.auction.dto.response;

import java.time.LocalDateTime;

public interface SimpleAuctionEndInterface {
    Long getAuction_id();
    String getAuction_title();
    LocalDateTime getStart_time();
    LocalDateTime  getEnd_time();
    String getProduct_name();
    String getCategory_name();
    Integer getOffer_price();
    Integer getFinal_price();
    Long getBuyer_id();
}
