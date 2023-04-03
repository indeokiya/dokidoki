package com.dokidoki.bid.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuctionInitialInfoResp {
    private Long highestPrice;
    private Long priceSize;
    private List<LeaderBoardMemberResp> leaderBoard;
}
