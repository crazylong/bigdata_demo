package com.coderlong.pattern.observer.event.myevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 4.现在就剩最后一个角色了，监听器有了，发布者有了，事件也有了，
 * 对，没错，还少一个触发者，毕竟要有触发者去触发事件啊
 */
@Component
public class Service {
    @Autowired
    private MyEventPublish publish;

    /**
     * 其中publish方法就是给客户端调用的，用来触发事件，
     * 可以很清楚的看到传入了new MyEvent(this)，
     * 这样发布者就可以知道我要触发什么事件和是谁触发了事件
     */
    public void publish(){
        publish.publish(new MyEvent(this));
    }
}
