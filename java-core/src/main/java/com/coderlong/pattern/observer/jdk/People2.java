package com.coderlong.pattern.observer.jdk;

import java.util.Observable;
import java.util.Observer;

public class People2 implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("开具一张图，内容全靠编");
    }
}
