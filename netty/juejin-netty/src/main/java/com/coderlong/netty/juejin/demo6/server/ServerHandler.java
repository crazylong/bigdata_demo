package com.coderlong.netty.juejin.demo6.server;

import com.coderlong.netty.juejin.demo6.protocol.Packet6;
import com.coderlong.netty.juejin.demo6.protocol.PacketCodeC6;
import com.coderlong.netty.juejin.demo6.protocol.request.LoginRequestPacket6;
import com.coderlong.netty.juejin.demo6.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    //服务端处理登录请求
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        System.out.println(new Date() + "：客户端开始登录....");
        ByteBuf requestByteBuf = (ByteBuf)msg;
        Packet6 packet = PacketCodeC6.INSTANCE.decode(requestByteBuf);

        if(packet instanceof LoginRequestPacket6){
            LoginRequestPacket6 loginRequestPacket = (LoginRequestPacket6)packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

            loginResponsePacket.setVersion(packet.getVersion());

            if(valid(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + "：登录成功！");
            } else {
                loginResponsePacket.setReason("账号咪喵校验失败！");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + "：登录失败！");
            }

            ByteBuf responseByteBuf = PacketCodeC6.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }


    private boolean valid(LoginRequestPacket6 loginRequestPacket) {
        return true;
    }
}
