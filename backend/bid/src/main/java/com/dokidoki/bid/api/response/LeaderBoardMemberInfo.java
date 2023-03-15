package com.dokidoki.bid.api.response;

import com.dokidoki.bid.api.request.AuctionBidReq;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LeaderBoardMemberInfo {
    private long memberId;
    private String name;
    private String email;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime bidTime;

    public static LeaderBoardMemberInfo of(AuctionBidReq req) {
        return LeaderBoardMemberInfo.builder()
                .memberId(req.getMemberId())
                .name(req.getName())
                .email(req.getEmail())
                .bidTime(LocalDateTime.now()).build();
    }
}
