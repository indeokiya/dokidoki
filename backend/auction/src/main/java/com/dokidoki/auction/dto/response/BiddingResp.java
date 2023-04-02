package com.dokidoki.auction.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BiddingResp {
    private String message;
    private Long[] auctionIdList;
}
