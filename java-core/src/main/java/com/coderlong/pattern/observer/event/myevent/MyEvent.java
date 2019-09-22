package com.coderlong.pattern.observer.event.myevent;

import org.springframework.context.ApplicationEvent;

/**
 * 利用Spring中的事件编程模型来自定义事件，并且发布事件
 * 1，我们需要定义一个事件，来实现ApplicationEvent接口，代表这是一个Application事件，
 * 其实上面所说的四个内置的事件也实现了ApplicationEvent接口
 */
public class MyEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MyEvent(Object source) {
        super(source);
    }
}
