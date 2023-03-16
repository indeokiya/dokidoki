package com.dokidoki.apigateway.config;

import com.dokidoki.apigateway.exception.handler.JwtExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerConfig {
    @Bean
    public ErrorWebExceptionHandler globalExceptionHandler() {
        return new JwtExceptionHandler();
    }
}
