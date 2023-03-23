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

    private Long product_id;

    private String name;  // '카테고리명 - 제품명'

    private String imgUrl;      // 대표 이미지 url

    private Integer saleCnt;     // 판매빈도
}
