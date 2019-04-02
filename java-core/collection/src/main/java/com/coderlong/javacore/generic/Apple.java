package com.coderlong.javacore.generic;

/**
 * @author Long Qiong
 * @create 2019/3/26
 */
public class Apple<T> {
    T info;

    public Apple(T info) {
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public static void main(String[] args) {
        Apple<String> a1 = new Apple<>("abc");

        Apple<Integer> a2 = new Apple<>(100);
    }


}
