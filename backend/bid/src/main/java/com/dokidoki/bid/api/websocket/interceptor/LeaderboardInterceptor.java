package com.dokidoki.bid.api.websocket.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
public class LeaderboardInterceptor implements HandshakeInterceptor {

    /**
     *  경로에서 auctionId 파싱해서 넘겨주기
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String path = request.getURI().getPath();
        String[] pathPart = path.split("/");
        long auctionId = Long.parseLong(pathPart[2]);
        log.info("auctionId:{}", auctionId);
        attributes.put("auctionId", auctionId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
