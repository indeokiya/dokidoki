package com.dokidoki.auction.dto.request;

import lombok.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LeaderboardHistoryRequest {
    private Long member_id;
    private Integer bid_price;
    private Timestamp bid_time;
}
