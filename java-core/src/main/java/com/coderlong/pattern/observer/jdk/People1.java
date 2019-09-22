package com.coderlong.pattern.observer.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * 实现jdk自带Observer接口，实现观察者角色
 */
public class People1 implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("小编真无聊");
    }
}
