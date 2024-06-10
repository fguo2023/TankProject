package com.msb.nettystudy.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket();
        socket.bind(new InetSocketAddress(8888));

        Socket s = socket.accept();
        System.out.println("a client connect!");
    }
}
