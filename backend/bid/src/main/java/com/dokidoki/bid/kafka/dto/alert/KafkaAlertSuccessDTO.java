package com.dokidoki.bid.kafka.dto.alert;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaAlertSuccessDTO {
    private AlertType type;
    private long productId;
    private String productName;
    private long auctionId;
    private int finalPrice;
}
