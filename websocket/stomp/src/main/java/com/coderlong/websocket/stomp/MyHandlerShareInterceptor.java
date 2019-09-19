package com.coderlong.websocket.stomp;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class MyHandlerShareInterceptor extends HttpSessionHandshakeInterceptor {
    public MyHandlerShareInterceptor(){

    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        return super.beforeHandshake(request, response, wsHandler, SessionUtils.attributesSessions);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        Map<String, Object> attributesSessions = SessionUtils.attributesSessions;
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
