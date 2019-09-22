package com.coderlong.pattern.observer.base;

public class BaseObserverTest {
    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        News news = new News("惊！楼主强奸母猪！", "aaaaaaaaaaaaaaaaaaaaaa");

//        Observer observer1 = new ConcreteObserver("zs", "10");
//        Observer observer2 = new ConcreteObserver("ls", "10");
//        subject.add(observer1);
//        subject.add(observer2);

        //lambda
        subject.add(System.out::println);

        subject.add(o -> {
            System.out.println("张三看了新闻：" + o);
        });

        subject.update(news);
    }
}
