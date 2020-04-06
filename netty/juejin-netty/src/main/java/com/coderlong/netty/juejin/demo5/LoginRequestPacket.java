package com.coderlong.netty.juejin.demo5;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.coderlong.netty.juejin.demo5.Command.LOGIN_REQUEST;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String userName;

    private String password;


    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
