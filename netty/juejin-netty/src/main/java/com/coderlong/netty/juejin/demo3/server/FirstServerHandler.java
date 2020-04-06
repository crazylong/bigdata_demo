package com.coderlong.netty.juejin.demo3.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //1.接收来自客户端数据
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println(new Date() + "：服务器读到数据 ->" + byteBuf.toString(StandardCharsets.UTF_8));

        //2.向客户端发送数据
        ByteBuf buf = getByteBuf(ctx);
        ctx.channel().writeAndFlush(buf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        //1. 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        //2. 准备数据，指定字符串的字符集为utf-8
        byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!".getBytes(StandardCharsets.UTF_8);

        //3. 填充数据到ByteBuf
        buffer.writeBytes(bytes);

        return buffer;

    }
}
