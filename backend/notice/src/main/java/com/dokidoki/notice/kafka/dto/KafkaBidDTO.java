package com.dokidoki.notice.kafka.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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
