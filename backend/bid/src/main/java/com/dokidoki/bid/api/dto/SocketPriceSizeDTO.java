package com.dokidoki.bid.api.dto;

import com.dokidoki.bid.api.response.LeaderBoardMemberResp;
import com.dokidoki.bid.kafka.dto.KafkaAuctionUpdateDTO;
import com.dokidoki.bid.kafka.dto.KafkaBidDTO;
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
