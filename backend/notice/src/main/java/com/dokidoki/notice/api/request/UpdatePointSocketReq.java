package com.dokidoki.notice.api.request;

import com.dokidoki.notice.common.enumtype.TradeType;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePointSocketReq {
    private Long user_id;
    private Long point;

    private String productName;
    private TradeType type;

    private Long earnedPoint;

    private String message;

    private boolean tradeSuccess;
}
