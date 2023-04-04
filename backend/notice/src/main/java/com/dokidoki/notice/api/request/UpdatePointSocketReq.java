package com.dokidoki.notice.api.request;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePointSocketReq {
    private Long user_id;
    private Long point;
    private String message;
}
