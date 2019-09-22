package com.coderlong.pattern.observer.event.applicationlistener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听Application事件
 * ContextRefreshedEvent
 * ContextStartedEvent
 * ContextStoppedEvent
 * ContextClosedEvent
 */
@Component
public class MyListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextRefreshedEvent){
            System.out.println("刷新了");
        }
    }
}
