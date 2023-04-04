package com.dokidoki.bid.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BidInfo {
    private Long y;
    private LocalDateTime x;

    public static BidInfo from(Long bidPrice, LocalDateTime bidTime) {
        BidInfo bidInfo = BidInfo.builder()
                .y(bidPrice)
                .x(bidTime)
                .build();
        return bidInfo;
    }
}
