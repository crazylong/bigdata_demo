package com.coderlong.netty.juejin.demo6.client;

import com.coderlong.netty.juejin.demo6.protocol.request.LoginRequestPacket6;
import com.coderlong.netty.juejin.demo6.protocol.Packet6;
import com.coderlong.netty.juejin.demo6.protocol.PacketCodeC6;
import com.coderlong.netty.juejin.demo6.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler6 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "：客户端开始登录");

        //创建登录对象
        LoginRequestPacket6 loginRequestPacket6 = new LoginRequestPacket6();
        loginRequestPacket6.setUserId(UUID.randomUUID().toString());
        loginRequestPacket6.setUserName("flash");
        loginRequestPacket6.setPassword("pwd");

        //编码
        ByteBuf buffer = PacketCodeC6.INSTANCE.encode(ctx.alloc(), loginRequestPacket6);

        //写数据
        //ctx.channel:获取当前连接，Netty对连接的抽象为Channel
        ctx.channel().writeAndFlush(buffer);
    }

    //客户端处理登录响应
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf byteBuf = (ByteBuf)msg;

        Packet6 packet = PacketCodeC6.INSTANCE.decode(byteBuf);

        if(packet instanceof  LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if(loginResponsePacket.isSuccess()){
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        }
    }
}
