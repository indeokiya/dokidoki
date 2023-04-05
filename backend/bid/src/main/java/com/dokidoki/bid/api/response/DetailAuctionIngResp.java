package com.dokidoki.bid.api.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DetailAuctionIngResp {
    String msg;
    DetailAuctionIngData data;
}
