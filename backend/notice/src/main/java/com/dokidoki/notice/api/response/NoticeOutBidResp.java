package com.dokidoki.notice.api.response;

import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class NoticeOutBidResp {
    private NoticeType type;
    private long productId;
    private String productName;
    private long auctionId;
    private int currentBidPrice;

    public static NoticeOutBidResp of(KafkaBidDTO dto) {
        NoticeOutBidResp resp = NoticeOutBidResp.builder()
                .type(NoticeType.OUTBID)
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .auctionId(dto.getAuctionId())
                .currentBidPrice(dto.getHighestPrice())
                .build();
        return resp;
    }

}
