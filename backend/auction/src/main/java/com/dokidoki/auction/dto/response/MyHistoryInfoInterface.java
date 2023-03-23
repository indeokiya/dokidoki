package com.dokidoki.auction.dto.response;

import java.time.LocalDateTime;

public interface MyHistoryInfoInterface {
    Long getAuction_id();
    String getCategory_name();
    String getBuyer_name();
    String getSeller_name();
    String getProduct_name();
    Integer getOffer_price();
    Integer getFinal_price();
    LocalDateTime getEnd_time();
}
