package com.dokidoki.notice.api.response;

import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocketBidInfoResp {
    private String type;
    private LeaderBoardMemberResp bidInfo;

    public static SocketBidInfoResp from(KafkaBidDTO dto) {
        // TODO - KafkaBidDTO 에서 담을 내용 더 있으면 알아서 넣기
        LeaderBoardMemberResp memberResp = LeaderBoardMemberResp.builder()
                .name(dto.getName()).build();

        SocketBidInfoResp resp = SocketBidInfoResp.builder()
                .type("bid")
                .bidInfo(memberResp)
                .build();

        return resp;

    }
}