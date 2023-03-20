package com.dokidoki.auction.dto.response;

import java.time.LocalDateTime;

// Join Query 데이터 DTO
public interface DetailAuctionEndInterface {
    String getAuction_title();
    LocalDateTime getStart_time();
    LocalDateTime  getEnd_time();
    String getProduct_name();
    String getCategory_name();
    String getDescription();
    String getSeller_name();
    String getBuyer_name();
    Integer getOffer_price();
    Integer getFinal_price();
}
