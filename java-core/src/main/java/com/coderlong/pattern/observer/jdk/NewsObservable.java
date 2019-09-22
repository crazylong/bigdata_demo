package com.coderlong.pattern.observer.jdk;

import java.util.Observable;

/**
 * 继承jdk自带Observable，实现被观察者角色
 */
public class NewsObservable extends Observable {
    public void update(){
        setChanged();
        //notifyObservers();
        notifyObservers("abc");
    }
}
