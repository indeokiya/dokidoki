package com.dokidoki.bid.api.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuctionBidReq {
    private long memberId; // TODO - JWT 토큰에서 가져오는 걸로 바꾸기
    private String name;
    private String email;
    private int currentHighestPrice;
    private int currentPriceSize;
}
