package com.coderlong.websocket.stomp;

public class HelloMessage {
    private String name;

    private Boolean first;

    public HelloMessage() {
    }

    public HelloMessage(String name, Boolean first) {
        this.name = name;
        this.first = first;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }
}
