package com.dokidoki.notice.api.response;

import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SocketBidInfoResp{
    private String type;
    private LeaderBoardMemberResp bidInfo;

    public SocketBidInfoResp() {}

    public SocketBidInfoResp(String type, LeaderBoardMemberResp bidInfo) {
        this.type = type;
        this.bidInfo = bidInfo;
    }

    public static SocketBidInfoResp from(KafkaBidDTO dto) {
        LeaderBoardMemberResp memberResp = LeaderBoardMemberResp.builder()
                .name(dto.getName())
                .bidPrice(dto.getHighestPrice())
                .bidTime(dto.getBidTime()).build();

        SocketBidInfoResp resp = SocketBidInfoResp.builder()
                .type("bid")
                .bidInfo(memberResp)
                .build();

        return resp;

    }
}