package com.dokidoki.bid.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RealtimeInterestInfo {
    private Long auctionId;
    private Long cnt;

    public static RealtimeInterestInfo from(Long auctionId, Long cnt) {
        return RealtimeInterestInfo.builder()
                .auctionId(auctionId)
                .cnt(cnt)
                .build();
    }
}
