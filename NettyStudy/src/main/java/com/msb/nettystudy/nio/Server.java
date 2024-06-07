package com.msb.nettystudy.nio;


import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

// nio has two ways to program: use one thread to do everything or use one thread + thread pool to do everything
// below uses one thread, netty uses two thread pools
// below uses only one thread
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open(); // channel 全双工
        ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
        ssc.configureBlocking(false); // very important, setting this blocking is false

        System.out.println("server started, listensing on : " + ssc.getLocalAddress());
        Selector selector = Selector.open(); // 大管家 轮询
        ssc.register(selector, SelectionKey.OP_ACCEPT); // 这个事件是用来关心客户端连接的

        while (true) {
            selector.select(); // 这个也是阻塞方法
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                handle(key);
            }

        }

    }

    private static void handle(SelectionKey key) {
        if (key.isAcceptable()) {
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(key.selector(), SelectionKey.OP_READ);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {

            }
        } else if (key.isReadable()) {
            SocketChannel sc = null;
            try {
                sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(512); // ByteBuffer is hard to use (only one pointer), so netty use ByteBuf (2 pointers, write pointer and read pointer)
                buffer.clear();
                int len = sc.read(buffer);
                if (len != -1) {
                    System.out.println(new String(buffer.array(), 0, len));
                }
                ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
                sc.write(bufferToWrite);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (sc != null) {
                    try {
                        sc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
