package com.coderlong.netty.juejin.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * 服务端启动流程
 */
public class NettyServer {
    public static void main(String[] args) {
        //1.接受新连接线程，主要负责创建新连接;
        // 表示监听端口，accept新连接的线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        //2.读取数据的线程，主要用于读取数据以及业务逻辑处理
        //表示处理每一条连接的数据读写的线程组
        NioEventLoopGroup worker = new NioEventLoopGroup();

        //3.创建引导类
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");

        serverBootstrap
                .group(boss, worker)//4.定义引导类线程模型
                .channel(NioServerSocketChannel.class)//5.定义服务端IO模型
                //给服务端的channel，也就是NioServerSocketChannel指定一些自定义属性，
                //然后可以通过channel.attr()取出这些属性
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                //给每一条连接指定自定义属性，然后可以通过channel.attr取出该属性
                .childAttr(clientKey, "clientValue")
                //给服务端channel设置一些属性，最常见的就是so_backlog
                //表示系统用于临时存放已完成三次握手的请求的队列的最大长度，
                //如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                //给每条连接设置一些TCP底层相关的属性
                //是否开启心跳机制，true是
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //是否开启Nagle算法，true表示关闭
                //如果要求高实时性，有数据发送时就马上发送，就关闭；
                //如果需要减少发送次数减少网络交互，就开启
                .childOption(ChannelOption.TCP_NODELAY, true)
                //6.给这个引导类创建一个ChannelInitializer，这里主要就是定义后续每条连接的数据读写，业务处理逻辑。
                //泛型NioSocketChannel就是Netty对NIO类型的连接的抽象
                //前面的NioServerSocketChannel也是对NIO类型的连接的抽象
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println(ch.attr(clientKey).get());
                    }
                })
                //用于指定在服务端启动过程中的一些逻辑，通常不用
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        System.out.println("服务端启动中...");
                    }
                })

        ;

        //7.绑定端口
        bind(serverBootstrap, 1000);

    }

    /**
     * 自动绑定递增端口
     * @param serverBootstrap
     * @param port
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        //bind为异步方法，调用之后马上返回，返回值是一个ChannelFuture
        //可以给这个ChannelFuture添加一个监听器GenericFutureListener，
        //然后在GenericFutureList的operationComplete方法里面，
        //我们可以监听端口是否绑定成功
        serverBootstrap.bind(port).addListener(future->{
            if(future.isSuccess()){
                System.out.println("端口【" + port + "】绑定成功!");
            } else {
                System.out.println("端口【" + port + "】绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
