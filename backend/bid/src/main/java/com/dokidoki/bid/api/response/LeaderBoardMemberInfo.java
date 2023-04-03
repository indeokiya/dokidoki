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
import java.time.ZoneId;

/**
 * DB SortedSet 에 유저 정보로 저장되는 클래스
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LeaderBoardMemberInfo {
    private Long memberId;
    private String name;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime bidTime;

    public static LeaderBoardMemberInfo of(AuctionBidReq req, Long memberId) {
        return LeaderBoardMemberInfo.builder()
                .memberId(memberId)
                .name(req.getName())
                .bidTime(LocalDateTime.now(ZoneId.of("Asia/Seoul"))).build();
    }
}
