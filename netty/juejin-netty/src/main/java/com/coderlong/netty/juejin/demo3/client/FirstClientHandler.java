package com.coderlong.netty.juejin.demo3.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 这个逻辑处理器为的就是在客户端建立连接成功之后，向服务端写数据
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(new Date() + ": 客户端写出数据!");

        //1.获取数据
        ByteBuf buffer = getByteBuf(ctx);

        //2.写数据
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(StandardCharsets.UTF_8));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        //1. 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        //2. 准备数据，指定字符串的字符集为utf-8
        byte[] bytes = "你好，闪电侠！".getBytes(StandardCharsets.UTF_8);

        //3. 填充数据到ByteBuf
        buffer.writeBytes(bytes);

        return buffer;

    }
}
