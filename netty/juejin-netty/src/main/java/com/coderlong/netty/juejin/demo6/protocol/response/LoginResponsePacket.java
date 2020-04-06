package com.coderlong.netty.juejin.demo6.protocol.response;

import com.coderlong.netty.juejin.demo6.protocol.Packet6;
import lombok.Data;

import static com.coderlong.netty.juejin.demo6.protocol.command.Command6.LOGIN_RESPONSE;


@Data
public class LoginResponsePacket extends Packet6 {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
