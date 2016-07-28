package com.jikexueyuan.server;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;


public class ServerSocket {
    public static void main(String[] args) {
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        acceptor.setHandler(new MySocketHandler());
        try {
            acceptor.bind(new InetSocketAddress(12580));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
