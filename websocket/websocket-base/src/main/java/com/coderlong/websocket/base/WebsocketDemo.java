package com.coderlong.websocket.base;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/websocket/{uid}")
public class WebsocketDemo {
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid") String uid) throws IOException {
        System.out.println("websocket已经连接" + session);
        session.getBasicRemote().sendText(uid + "您好！欢迎登录系统");
    }

    @OnClose
    public void onClose(Session session){
        System.out.println("websocket已经关闭" + session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("收到客户端发来的消息 --->" + message);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        session.getBasicRemote().sendText("消息已收到");
    }
}
