package com.dokidoki.notice.api.response;

import com.dokidoki.notice.kafka.dto.KafkaBidDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SocketBidInfoResp{
    private String type;
    private LeaderBoardMemberResp bidInfo;

    public SocketBidInfoResp() {}

    public SocketBidInfoResp(String type, LeaderBoardMemberResp bidInfo) {
        this.type = type;
        this.bidInfo = bidInfo;
    }

    public static SocketBidInfoResp from(KafkaBidDTO dto, Long myBidNum) {
        LeaderBoardMemberResp memberResp = LeaderBoardMemberResp.builder()
                .name(dto.getName())
                .bidPrice(dto.getHighestPrice())
                .bidNum(myBidNum)
                .bidTime(dto.getBidTime()).build();

        SocketBidInfoResp resp = SocketBidInfoResp.builder()
                .type("bid")
                .bidInfo(memberResp)
                .build();

        return resp;

    }
}