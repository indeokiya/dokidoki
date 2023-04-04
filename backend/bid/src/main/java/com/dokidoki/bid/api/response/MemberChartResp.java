package com.dokidoki.bid.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberChartResp {
    private String name;
    private List<BidInfo> bidInfos;

    public void updateName(String name) {
        String modifiedName;
        if (name == null || name.length() < 2) {
            modifiedName = name;
        } else {
            modifiedName = name.substring(0, 1) + "*" + name.substring(2);
        }

        this.name = modifiedName;
    }

}
