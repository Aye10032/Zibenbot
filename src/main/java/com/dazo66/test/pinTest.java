package com.dazo66.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class pinTest {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket();
        SocketAddress add = new InetSocketAddress("127.0.0.1", 1080);
        try {
            s.connect(add, 3000);// 超时3秒
            System.out.println("conn susses");
        } catch (IOException e) {
            //连接超时需要处理的业务逻辑
        }
        s.close();
    }

}
