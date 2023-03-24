package com.dokidoki.auction.dto.response;

import java.time.LocalDateTime;

public interface SimpleAuctionIngInterface {
    Long getAuction_id();
    String getAuction_title();
    LocalDateTime  getEnd_time();
    String getProduct_name();
    String getCategory_name();
    Integer getOffer_price();
    Integer getCur_price();
    String getMeeting_place();
    Long getSeller_id();
}
