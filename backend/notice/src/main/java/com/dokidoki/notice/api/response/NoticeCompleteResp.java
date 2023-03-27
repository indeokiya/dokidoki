package com.dokidoki.notice.api.response;

import com.dokidoki.notice.kafka.dto.KafkaAuctionEndDTO;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class NoticeCompleteResp {
    private NoticeType type;
    private long productId;
    private String productName;
    private long auctionId;
    private int finalPrice;

    public static NoticeCompleteResp of(KafkaAuctionEndDTO dto) {
        NoticeCompleteResp resp = NoticeCompleteResp.builder()
                .type(NoticeType.SALE_COMPLETE)
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .auctionId(dto.getAuctionId())
                .finalPrice(dto.getFinalPrice())
                .build();
        return resp;
    }

}
