package com.coderlong.websocket.stomp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket主要的消息类
 * @author 侯叶飞
 */
 //onfigurator = WebsocketConfig.class 该属性就是我上面提到我们可以自己配置的东西
@ServerEndpoint(value = "/gs-guide-websocket")
@Component
//@Slf4j
public class WebSocket {
    private Logger log = LoggerFactory.getLogger(WebSocket.class);
    /*每个浏览器连接都会有一个新的会话对象*/
    private Session session;
    /*用来存储每个会话的session,静态的不会被实例化*/
    private static CopyOnWriteArraySet<WebSocket> webSocketSets = new CopyOnWriteArraySet<>();
    /**
     * 主要用来监听连接建立，config用来获取WebsocketConfig中的配置信息
     * @param session
     * @param config
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.info("config:{}", config.getUserProperties().get("name"));
        log.info("session:{}", config.getUserProperties().get("sessionid"));
        this.session = session;
        webSocketSets.add(this);
        log.info("【websocket消息】有新的连接, 总数:{}", webSocketSets.size());
    }

    @OnClose
    public void onClose() {
        webSocketSets.remove(this);
        log.info("【websocket消息】连接断开, 总数:{}", webSocketSets.size());
    }

    @OnError
    public void onError(Throwable e, Session session) {
        webSocketSets.remove(this);
        log.info("【websocket消息】连接出错或未关闭socket：" + e.getMessage());

    }

    @OnMessage
    public void onMessage(String message, Session session) {
        for(WebSocket ws:webSocketSets){
            ws.session.getAsyncRemote().sendText("广播："+message);
        }
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }
}