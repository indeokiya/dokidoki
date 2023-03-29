package com.dokidoki.notice.api.response;

import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class NoticeOutBidResp implements NoticeResp {
    private NoticeType type;
    private long productId;
    private String productName;
    private long auctionId;
    private int currentBidPrice;
    private LocalDateTime timeStamp;

    public static NoticeOutBidResp of(KafkaBidDTO dto) {
        NoticeOutBidResp resp = NoticeOutBidResp.builder()
                .type(NoticeType.OUTBID)
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .auctionId(dto.getAuctionId())
                .currentBidPrice(dto.getHighestPrice())
                .timeStamp(dto.getBidTime())
                .build();
        return resp;
    }

    @Override
    public NoticeType typeIs() {
        return type;
    }
}
