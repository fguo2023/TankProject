package com.msb.nettystudy.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

// nio has two ways to program: use one thread to do everything or use one thread + thread pool to do everything
// below uses one thread, netty uses two thread pools
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open(); // channel 全双工
        ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
        ssc.configureBlocking(false); // very important, setting this blocking is false

        System.out.println("server started, listensing on : " + ssc.getLocalAddress());
        Selector selector = Selector.open(); // 大管家 轮询
        ssc.register(selector, SelectionKey.OP_ACCEPT); // 这个事件是用来关心客户端连接的

        while(true){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while(it.hasNext()){
                SelectionKey key = it.next();
                it.remove();
                handle(key);
            }

        }

    }

    private static void handle(SelectionKey key) {
        if(key.isAcceptable()){
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
