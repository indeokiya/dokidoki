package com.dokidoki.notice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 브로커를 등록하는 코드
        // 보통 topic 은 한 명이 메시지를 발행했을 때, 해당 토픽을 구독하고 있는 n명 에게 메시지를 뿌려야 할 때 사용
        // queue : 한 명이 message 를 발행했을 때, 발행한 한 명에게 다시 정보를 보내는 경우에 사용
        registry.enableSimpleBroker("/topic");
        // 도착 경로에 대한 prefix 설정 "/app" 이라고 설정하면,
        // /topic/hello 라는 토픽에 구독을 신청했을 때, 여기에 app을 쓰면 실제 경로는 /app/topic/hello 가 됨
        registry.setApplicationDestinationPrefixes("/");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 연결할 소켓 엔드포인트를 지정하는 코드
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*");
    }

}
