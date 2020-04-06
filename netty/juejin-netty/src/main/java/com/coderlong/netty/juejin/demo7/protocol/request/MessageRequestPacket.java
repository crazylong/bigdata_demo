package com.coderlong.netty.juejin.demo7.protocol.request;

import lombok.Data;

import static com.coderlong.netty.juejin.demo7.protocol.command.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {
    private String message;

    public Byte getCommand(){ return MESSAGE_REQUEST;}
}
