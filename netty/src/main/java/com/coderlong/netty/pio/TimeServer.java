package com.coderlong.netty.pio;

import com.coderlong.netty.bio.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Long Qiong
 * @create 2019/4/15
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        try{
            port = Integer.valueOf(args[0]);
        } catch (Exception e){

        }

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            Socket socket = null;
            TimeServerExecutorPoolHandler timeServerExecutorPoolHandler = new TimeServerExecutorPoolHandler(10, 50);
            while (true){
                socket = server.accept();
                timeServerExecutorPoolHandler.executor(new TimeServerHandler(socket));
            }
        } finally {
            if(server != null){
                server.close();
                server = null;
            }
        }
    }
}
