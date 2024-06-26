package com.msb.tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
         /* this is bio to write the server:
        ServerSocket socket = new ServerSocket();
        socket.bind(new InetSocketAddress(8888));

        Socket s = socket.accept();
        System.out.println("a client connect!");*/

        // Netty:
        // TCP nagle algorithm, for the game, we should stop this algorithm by adding the option in the chain
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
                            pl.addLast(new MsgEncoder()).
                                    addLast(new MsgDecoder()).
                                    addLast(new ServerChildHandler());
                        }
                    })
                    //.childOption(ChannelOption.TCP_NODELAY, true) // stop using nagle algorithm
                    .bind(8888)
                    .sync(); // 加sync的目的就是因为其他的方法都是异步的，想要让它阻塞，必须加sync让它同步

            ServerFrame.INSTANCE.updateServerMessage("server start!!");
            //[id: 0x112f88b8, L:/127.0.0.1:8888 - R:/127.0.0.1:54540] R is the client side
            f.channel().closeFuture().sync(); // close() -> ChannelFuture only if someone invoke close() method, otherwise, it will be blocked here
        } catch (Exception e) {
            e.printStackTrace();
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

    // server is act as the server to forward the message to all the other clients. client 1 -> server -> client 2
    // once client 1's tank starts to move, then it needs to broadcast all the message to other client
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        try {
//            TankJoinMsg tm = (TankJoinMsg) msg;
//            System.out.println(tm);
//        } finally {
//            if (msg != null) {
//                ReferenceCountUtil.release(msg); // 防止内存泄漏
//            }
//        }
        ServerFrame.INSTANCE.updateServerMessage(msg.toString());
        Server.clients.writeAndFlush(msg); // 转发
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        Server.clients.remove(ctx.channel());
        ctx.close();
    }
}