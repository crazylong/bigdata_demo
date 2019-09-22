package com.coderlong.pattern.observer.event.myevent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 2.定义一个监听器，当然，在这里需要监听MyEvent事件
 */
@Component
public class MyListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("我订阅的事件已经到达");
    }
}
