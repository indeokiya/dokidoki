package com.dokidoki.notice.api.response;

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
public class LeaderBoardMemberResp {
    private String name;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime bidTime;
    private Long bidPrice;
    private Long bidNum;

    public static LeaderBoardMemberResp of(LeaderBoardMemberInfo info, Long bidPrice) {
        // 개인정보 가리기
        String name = info.getName();
        String modifiedName;
        if (name == null || name.length() < 2) {
            modifiedName = name;
        } else {
            modifiedName = name.substring(0, 1) + "*" + name.substring(2);
        }

        return LeaderBoardMemberResp.builder()
                .name(modifiedName)
                .bidTime(info.getBidTime())
                .bidNum(info.getBidNum())
                .bidPrice(bidPrice).build();
    }



}
