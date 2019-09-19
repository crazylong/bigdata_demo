package com.coderlong.websocket.stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;

@Controller
public class GreetingController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Resource
    SubProtocolWebSocketHandler subProtocolWebSocketHandler;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws InterruptedException {
        subProtocolWebSocketHandler.getStatsInfo();
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Greeting greeting(String name, Boolean first) throws InterruptedException {
//        Thread.sleep(1000);
//        return new Greeting("Hello, " + HtmlUtils.htmlEscape(name) + "!");
//    }

    @MessageMapping("/hello2")
    public void greeting2(HelloMessage message) throws InterruptedException {
        Thread.sleep(1000);
        //intervalGreeting();
        intervalGreeting2();
    }

    @MessageMapping("/hello3/{roomId}")
    @SendTo("/topic/greetings3")
    public Greeting greeting3(HelloMessage message, @DestinationVariable String roomId) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(roomId);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


    @MessageMapping("/hello4")
    @SendTo("/topic/greetings4")
    public Greeting greeting4(HelloMessage helloMessage, Message<?> message) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        System.out.println("SessionId: " + sha.getSessionId());

        return new Greeting("Hello, " + HtmlUtils.htmlEscape(helloMessage.getName()) + "!");
    }

    /**
     * 好像不行
     * @return
     */
    @SendTo("/topic/greetings2")
    //@Scheduled(fixedDelay = 1000)
    public Greeting intervalGreeting(){
        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!" + System.currentTimeMillis());
        return new Greeting("Hello, oo!" + System.currentTimeMillis());
    }


    //@Scheduled(fixedDelay = 1000)
    public void intervalGreeting2(){
        while (true){
            messagingTemplate.convertAndSend("/topic/greetings2", new Greeting("Hello, oo!" + System.currentTimeMillis()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
