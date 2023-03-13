package com.dokidoki.auction.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductResp {

    private String name;        // 제품명

    private String imgUrl;      // 대표 이미지 url

    private int saleCnt;     // 판매빈도
}
