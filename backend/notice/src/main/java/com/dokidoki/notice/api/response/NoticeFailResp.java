package com.dokidoki.notice.api.response;

import com.dokidoki.notice.kafka.dto.KafkaAuctionEndDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class NoticeFailResp implements NoticeResp {
    private NoticeType type;
    private long productId;
    private String productName;
    private long auctionId;
    private int finalPrice;
    private int myFinalPrice;
    private LocalDateTime timeStamp;

    public static NoticeFailResp of(KafkaAuctionEndDTO dto, int myFinalPrice) {
        NoticeFailResp resp = NoticeFailResp.builder()
                .type(NoticeType.PURCHASE_FAIL)
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .auctionId(dto.getAuctionId())
                .finalPrice(dto.getFinalPrice())
                .myFinalPrice(myFinalPrice)
                .timeStamp(dto.getEndTime())
                .build();
        return resp;
    }

    @Override
    public NoticeType typeIs() {
        return type;
    }
}
