package com.dokidoki.apigateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final HttpStatus status;
    private final String message;
}
