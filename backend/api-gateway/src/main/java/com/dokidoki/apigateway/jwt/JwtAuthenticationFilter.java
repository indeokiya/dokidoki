package com.dokidoki.apigateway.jwt;


import com.dokidoki.apigateway.exception.JwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
@Slf4j
public class JwtAuthenticationFilter implements GatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            log.info(request.getPath().toString());

            if(!request.getHeaders().containsKey("Authorization"))
                throw new JwtAuthenticationException("로그인이 필요한 서비스입니다.", HttpStatus.FORBIDDEN);

            String accessToken = request.getHeaders().get("Authorization").get(0).substring(7);

            JwtUtil.isValidToken(accessToken);

            return chain.filter(exchange);
        };
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    public static class Config {
        // Put the configuration properties
    }
}