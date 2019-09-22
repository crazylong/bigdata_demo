package com.coderlong.pattern.observer.jdk;

public class JdkObserverTest {
    public static void main(String[] args) {
        NewsObservable newsObservable = new NewsObservable();
        newsObservable.addObserver(new People1());
        newsObservable.addObserver(new People2());
        newsObservable.update();
    }
}
