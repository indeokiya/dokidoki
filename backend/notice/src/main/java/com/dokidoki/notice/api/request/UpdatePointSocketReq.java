package com.dokidoki.notice.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePointSocketReq {
    private Long user_id;
    private Long point;
}
