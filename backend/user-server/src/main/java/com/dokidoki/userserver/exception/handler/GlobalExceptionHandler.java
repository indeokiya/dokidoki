package com.dokidoki.userserver.exception.handler;

import com.dokidoki.userserver.exception.CustomException;
import com.dokidoki.userserver.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<?> handleAllException(Exception e) {
//        e.printStackTrace();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("전역 에러"));
//    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<?> handleCustomException(CustomException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(ErrorCode.builder().timestamp(e.getTimestamp().toString())
                .message(e.getMessage())
                .status(e.getStatus().value()).build());
    }
}
