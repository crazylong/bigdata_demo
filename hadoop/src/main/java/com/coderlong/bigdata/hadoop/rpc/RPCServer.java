package com.coderlong.bigdata.hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;

/**
 * @author Long Qiong
 * @create 2018/8/13
 */
public class RPCServer implements Hello {
    @Override
    public String say(String words) {
        System.out.println(words);
        return "recived " + words;
    }

    public static void main(String[] args) {
        try {
            Server server = new RPC.Builder(new Configuration())
                    .setInstance(new RPCServer())
                    .setProtocol(Hello.class)
                    .setBindAddress("127.0.0.1")
                    .setPort(6666)
                    .build();
            //启动服务
            server.start();
            System.out.println("server is started... ");
        } catch (Exception e){

        }
    }
}
