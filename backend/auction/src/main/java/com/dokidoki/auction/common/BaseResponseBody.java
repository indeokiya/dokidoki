package com.dokidoki.auction.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseBody {
    String msg;
    Object data;

    public static BaseResponseBody of(String msg) {
        return BaseResponseBody.builder()
                .msg(msg)
                .build();
    }

    public static BaseResponseBody of(String msg, Object data) {
        return BaseResponseBody.builder()
                .msg(msg)
                .data(data)
                .build();
    }
}
