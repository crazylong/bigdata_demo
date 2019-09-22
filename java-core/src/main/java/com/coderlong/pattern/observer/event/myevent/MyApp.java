package com.coderlong.pattern.observer.event.myevent;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 6.客户端
 */
public class MyApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        context.getBean(Service.class).publish();
    }
}
