package com.dokidoki.notice.api.dto;

import com.dokidoki.notice.kafka.dto.KafkaAuctionUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocketPriceSizeDTO {
    private String type;
    private int priceSize;
    public static SocketPriceSizeDTO from(KafkaAuctionUpdateDTO dto) {
        SocketPriceSizeDTO resDTO = SocketPriceSizeDTO.builder()
                .type("price_size")
                .priceSize(dto.getPriceSize()).build();

        return resDTO;

    }


}