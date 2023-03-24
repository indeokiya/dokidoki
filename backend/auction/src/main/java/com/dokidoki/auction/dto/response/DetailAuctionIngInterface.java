package com.dokidoki.auction.dto.response;

import java.time.LocalDateTime;

public interface DetailAuctionIngInterface {
    String getAuction_title();
    LocalDateTime getStart_time();
    LocalDateTime  getEnd_time();
    String getProduct_name();
    String getCategory_name();
    String getMeeting_place();
    String getDescription();
    Long getSeller_id();
    String getSeller_name();
    Integer getOffer_price();
    Integer getPrice_size();
    Integer getHighest_price();
}
