package com.coderlong.bigdata.hadoop.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author Long Qiong
 * @create 2018/8/13
 */
public class RPCClient {
    public static void main(String[] args) throws InterruptedException, IOException {


        while (true){
            Hello hello = RPC.getProxy(Hello.class, 1, new InetSocketAddress("127.0.0.1", 6666), new Configuration());
            String res = hello.say("fuck hadoop");
            System.out.println(res);
            Thread.sleep(3000);
        }
    }
}
