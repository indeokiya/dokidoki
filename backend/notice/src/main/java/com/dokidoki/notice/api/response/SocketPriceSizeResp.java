package com.dokidoki.notice.api.response;

import com.dokidoki.notice.kafka.dto.KafkaAuctionUpdateDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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