package com.coderlong.pattern.observer.base;

/**
 * 具体观察者1
 */
public class ConcreteObserver implements Observer {
    private String name;
    private String age;

    public ConcreteObserver(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void update(News news) {
        System.out.println(getName() + "收到消息");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
