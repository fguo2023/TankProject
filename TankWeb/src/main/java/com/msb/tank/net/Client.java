package com.msb.tank.net;

import com.msb.tank.Tank;
import com.msb.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {
    private Channel channel = null;

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
                                channel = future.channel();
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

    public void send(String msg) {
        ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
        channel.writeAndFlush(buf);
    }

    public void closeConnect() {
        this.send("_bye_");
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().
                addLast(new TankJoinMsgEncoder()).
                addLast(new TankJoinMsgDecoder()).
                addLast(new ClientHandler());
    }
}

// 处理接收到的消息 we previously use ChannelInboundHandlerAdapter, only when you have one type of the message, you could use SimpleChannelInboundHandler
// if you are deal with more than one type of the msg, you may use
//class ClientHandler1 extends ChannelInboundHandlerAdapter{
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
//    }
//}
class ClientHandler extends SimpleChannelInboundHandler<TankJoinMsg> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // channel 第一次连上可用，写出一个字符串
        // netty 中的bytebuf效率特别高， 因为是direct memory 直接访问内存，但是跳过垃圾回收机制。就必须释放
//        ByteBuf buf = Unpooled.copiedBuffer("hello".getBytes());
//        ctx.writeAndFlush(buf);sous
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TankJoinMsg msg) throws Exception {
        if (msg.id.equals(TankFrame.INSTANCE.getMainTank().getId()) || TankFrame.INSTANCE.findByUUID(msg.id) != null)return;
        System.out.println(msg); // read the TankJoinMsg to th sever
        Tank t = new Tank(msg);
        TankFrame.INSTANCE.addTank(t);
        // send a new TankJoinMsg to the new joined tank
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }
}
