package com.msb.nettystudy.s02;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    public void connect() {
        // 线程池
        EventLoopGroup group = new NioEventLoopGroup(1);

        Bootstrap b = new Bootstrap(); // 辅助启动类
        try {
            ChannelFuture f = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888)
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (future.isSuccess()) {
                                System.out.println("connected");
                            } else {
                                System.out.println("not connected");
                            }
                        }
                    }).sync();

            f.channel().closeFuture().sync(); //这句话要好好理解，因为这里有个正常的关闭过程
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ClientHandler());
    }
}

class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // channel 第一次连上可用，写出一个字符串
        // netty 中的bytebuf效率特别高， 因为是direct memory 直接访问内存，但是跳过垃圾回收机制。就必须释放
        ByteBuf buf = Unpooled.copiedBuffer("hello".getBytes());
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = null;
        try {
            buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            System.out.println("Client channel read: " + new String(bytes));
            //System.out.println(buf);
            //System.out.println(buf.refCnt()); // refCnt is 1 means is not released
        } finally {
//            if(buf != null){
//                ReferenceCountUtil.release(buf); // 防止内存泄漏
//            }
            //System.out.println(buf.refCnt());
        }
    }
}
