package com.dokidoki.notice.api.dto;

import com.dokidoki.notice.api.response.LeaderBoardMemberResp;
import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocketBidInfoDTO {
    private String type;
    private LeaderBoardMemberResp bidInfo;

    public static SocketBidInfoDTO from(KafkaBidDTO dto) {
        // TODO - KafkaBidDTO 에서 담을 내용 더 있으면 알아서 넣기
        LeaderBoardMemberResp resp = LeaderBoardMemberResp.builder()
                .name(dto.getName())
                .email(dto.getEmail()).build();

        SocketBidInfoDTO resDTO = SocketBidInfoDTO.builder()
                .type("bid")
                .bidInfo(resp)
                .build();

        return resDTO;

    }
}