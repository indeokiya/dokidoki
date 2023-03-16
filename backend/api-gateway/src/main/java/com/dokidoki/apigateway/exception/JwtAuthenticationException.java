package com.dokidoki.apigateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtAuthenticationException extends RuntimeException {
    private final HttpStatus status;
    private final String msg;
    public JwtAuthenticationException(String msg, HttpStatus status) {
        this.msg = msg;
        this.status = status;
    }
}
