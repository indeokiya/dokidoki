package com.dokidoki.bid.api.response;

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
    private String email;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime bidTime;
    private int bidPrice;

    public static LeaderBoardMemberResp of(LeaderBoardMemberInfo info, int bidPrice) {
        // 개인정보 가리기
        String name = info.getName();
        String modifiedName;
        if (name.length() >= 2) {
            modifiedName = name.substring(0, 1) + "*" + name.substring(2);
        } else {
            modifiedName = name;
        }

        String modifiedEmail = getModifiedEmail(info.getEmail());

        return LeaderBoardMemberResp.builder()
                .name(modifiedName)
                .email(modifiedEmail)
                .bidTime(info.getBidTime())
                .bidPrice(bidPrice).build();
    }

    public static String getModifiedEmail(String email) {
        StringBuilder sb = new StringBuilder();
        int indexOfAt = email.indexOf('@');
        sb.append(email.substring(0, 2));
        for (int i = 0; i < indexOfAt - 2; i++) {
            sb.append("*");
        }
        sb.append(email.substring(indexOfAt));
        return sb.toString();
    }

}
