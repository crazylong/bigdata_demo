package com.coderlong.pattern.observer.base;

/**
 * 具体主题（被观察者）
 */
public class ConcreteSubject implements Subject {
    /**
     * 发布消息
     * @param news
     */
    @Override
    public void update(News news) {
        for (Observer observer : OBSERVER_LIST) {
            observer.update(news);
        }
    }
}
