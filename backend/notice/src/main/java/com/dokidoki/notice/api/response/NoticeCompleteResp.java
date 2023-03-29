package com.dokidoki.notice.api.response;

import com.dokidoki.notice.kafka.dto.KafkaAuctionEndDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class NoticeCompleteResp implements NoticeResp {
    private NoticeType type;
    private long productId;
    private String productName;
    private long auctionId;
    private int finalPrice;
    private LocalDateTime timeStamp;

    public static NoticeCompleteResp of(KafkaAuctionEndDTO dto) {
        NoticeCompleteResp resp = NoticeCompleteResp.builder()
                .type(NoticeType.SALE_COMPLETE)
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .auctionId(dto.getAuctionId())
                .finalPrice(dto.getFinalPrice())
                .timeStamp(dto.getEndTime())
                .build();
        return resp;
    }

    @Override
    public NoticeType typeIs() {
        return type;
    }
}
