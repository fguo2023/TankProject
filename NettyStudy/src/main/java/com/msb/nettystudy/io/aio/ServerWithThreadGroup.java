package com.msb.nettystudy.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerWithThreadGroup {
    public class Server {
        public static void main(String[] args) throws IOException, InterruptedException {
            ExecutorService executorService = Executors.newCachedThreadPool();
            AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
        // pass the thradGroup to the aync server socket channel
            final AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open(threadGroup).bind(new InetSocketAddress(8888));
            serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel client, Object attachment) {
                    serverSocketChannel.accept(null, this);
                    try {
                        System.out.println(client.getRemoteAddress());
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                            @Override
                            public void completed(Integer result, ByteBuffer attachment) {
                                attachment.flip();
                                System.out.println(new String(attachment.array(), 0, result));
                                client.write(ByteBuffer.wrap("HelloClient".getBytes()));
                            }

                            @Override
                            public void failed(Throwable exc, ByteBuffer attachment) {
                                exc.printStackTrace();
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    exc.printStackTrace();
                }
            });
            Thread.sleep(1000);
        }
    }

}
