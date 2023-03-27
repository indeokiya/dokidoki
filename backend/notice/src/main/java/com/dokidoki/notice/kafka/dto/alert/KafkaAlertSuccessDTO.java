package com.dokidoki.notice.kafka.dto.alert;

import com.dokidoki.notice.db.entity.AuctionRealtime;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class KafkaAlertSuccessDTO {
    private NoticeType type;
    private long productId;
    private String productName;
    private long auctionId;
    private int finalPrice;

    public static KafkaAlertSuccessDTO of(AuctionRealtime auctionRealtime) {
        KafkaAlertSuccessDTO dto = KafkaAlertSuccessDTO.builder()
                .type(NoticeType.PURCHASE_SUCCESS)
                .productId(auctionRealtime.getProductId())
                .productName(auctionRealtime.getProductName())
                .auctionId(auctionRealtime.getAuctionId())
                .finalPrice(auctionRealtime.getHighestPrice())
                .build();
        return dto;
    }
}
