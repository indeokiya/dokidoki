package com.dokidoki.userserver.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorCode {
    private String message;
    private Integer status;
    private String timestamp;
}
