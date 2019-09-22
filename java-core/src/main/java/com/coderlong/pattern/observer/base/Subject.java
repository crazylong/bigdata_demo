package com.coderlong.pattern.observer.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽稀对象（被观察者）
 */
public interface Subject {
    List<Observer> OBSERVER_LIST = new ArrayList<>();
    default void add(Observer observer){
        OBSERVER_LIST.add(observer);
    }

    default void remove(Observer observer){
        OBSERVER_LIST.remove(observer);
    }

    void update(News news);
}
