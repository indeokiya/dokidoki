package com.dokidoki.auction.dto.request;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LeaderboardHistoryRequest {
    private Long member_id;
    private Integer bid_price;
    private LocalDateTime bid_time;
}
