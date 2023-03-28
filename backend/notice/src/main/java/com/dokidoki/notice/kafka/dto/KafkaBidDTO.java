package com.dokidoki.notice.kafka.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaBidDTO {

    private long beforeWinnerId;
    private long memberId;
    private long auctionId;
    private String name;
    private int highestPrice;
    private String productName;
    private long productId;
    private LocalDateTime bidTime;

}
