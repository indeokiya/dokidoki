package com.dokidoki.notice.kafka.dto.alert;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaAlertFailDTO {
    private NoticeType type;
    private long productId;
    private String productName;
    private long auctionId;
    private int finalPrice;
    private int myFinalPrice;

}
