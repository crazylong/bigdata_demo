package com.coderlong.pattern.observer.event.myevent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * 3.现在有了事件，也有了监听器，是不是还少了发布者，不然谁去发布事件呢
 */
@Component
public class MyEventPublish implements ApplicationEventPublisherAware {
   private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void publish(Object obj){
        this.publisher.publishEvent(obj);
    }

    /**
     * 发布者，需要实现ApplicationEventPublisherAware 接口，
     * 重写publish方法，顾名思义，这就是发布方法，那么方法的参数obj是干嘛的呢，
     * 作为发布者，应该需要知道我要发布什么事件，以及事件来源（是谁触发的）把，
     * 这个obj就是用来存放这样的数据的，
     * 当然，这个参数需要我们手动传入进去。setApplicationEventPublisher是Spring内部主动调用的，
     * 可以简单的理解为初始化发布者。
     */
}
