package com.coderlong.netty.juejin.demo6.protocol.request;

import com.coderlong.netty.juejin.demo6.protocol.Packet6;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.coderlong.netty.juejin.demo6.protocol.command.Command6.LOGIN_REQUEST;


@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket6 extends Packet6 {
    private String userId;

    private String userName;

    private String password;


    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
