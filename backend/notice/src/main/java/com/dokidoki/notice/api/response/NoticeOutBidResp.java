package com.dokidoki.notice.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeOutBidResp {
    private NoticeType type;
    private long productId;
    private String productName;
    private long auctionId;
    private int currentBidPrice;

}
