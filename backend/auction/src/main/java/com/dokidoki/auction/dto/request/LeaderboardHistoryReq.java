package com.dokidoki.auction.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LeaderboardHistoryReq {
    private Long member_id;
    private Long bid_price;
    private LocalDateTime bid_time;
}
