package com.msb.nettystudy.s02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static void main(String[] args) throws Exception {
        /* this is bio to write the server:
        ServerSocket socket = new ServerSocket();
        socket.bind(new InetSocketAddress(8888));

        Socket s = socket.accept();
        System.out.println("a client connect!");*/

        // Netty:
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 负责客户端连接
        EventLoopGroup workerGroup = new NioEventLoopGroup(2); // 之后交给客户端处理
        try {
            ServerBootstrap b = new ServerBootstrap();
            ChannelFuture f = b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pl = ch.pipeline();
                            pl.addLast(new ServerChildHandler());
                        }
                    })
                    .bind(8888)
                    .sync(); // 加sync的目的就是因为其他的方法都是异步的，想要让它阻塞，必须加sync让它同步

            System.out.println("server started");
            //[id: 0x112f88b8, L:/127.0.0.1:8888 - R:/127.0.0.1:54540] R is the client side
            f.channel().closeFuture().sync(); // close() -> ChannelFuture only if someone invoke close() method, otherwise, it will be blocked here
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

class ServerChildHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = null;
        try {
            buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            System.out.println(new String(bytes));

            Server.clients.writeAndFlush(msg);
            //System.out.println(buf);
            //System.out.println(buf.refCnt()); // refCnt is 1 means is not released
        } finally {
//            if (buf != null) {
//                ReferenceCountUtil.release(buf); // 防止内存泄漏
//            }
            //System.out.println(buf.refCnt());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}