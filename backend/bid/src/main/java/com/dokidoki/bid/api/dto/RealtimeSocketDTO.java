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
public class RealtimeSocketDTO {
    private LeaderBoardMemberResp resp;
    private int priceSize;

    public static RealtimeSocketDTO from(KafkaBidDTO dto) {
        // TODO - KafkaBidDTO 에서 담을 내용 더 있으면 알아서 넣기
        LeaderBoardMemberResp resp = LeaderBoardMemberResp.builder()
                .name(dto.getName())
                .email(dto.getEmail()).build();

        RealtimeSocketDTO resDTO = RealtimeSocketDTO.builder()
                .resp(resp)
                .build();

        return resDTO;
    }

    public static RealtimeSocketDTO from(KafkaAuctionUpdateDTO dto) {
        RealtimeSocketDTO resDTO = RealtimeSocketDTO.builder()
                .priceSize(dto.getPriceSize()).build();

        return resDTO;
    }
}
