package com.dokidoki.notice.api.response;

import com.dokidoki.notice.kafka.dto.KafkaAuctionUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SocketPriceSizeResp {
    private String type;
    private int priceSize;
    public static SocketPriceSizeResp from(KafkaAuctionUpdateDTO dto) {
        SocketPriceSizeResp resp = SocketPriceSizeResp.builder()
                .type("price_size")
                .priceSize(dto.getPriceSize()).build();

        return resp;

    }


}