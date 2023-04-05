package com.dokidoki.auction.dto.response;

import com.dokidoki.auction.enumtype.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePointSocketRes {
    private Long user_id;
    private Long point;

    private String productName;
    private TradeType type;

    private Long earnedPoint;

    private String message;

    private boolean tradeSuccess;
}
