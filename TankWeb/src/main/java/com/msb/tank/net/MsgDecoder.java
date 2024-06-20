package com.msb.tank.net;

import com.msb.tank.Tank;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // get the total length of different ByteBuf in depends on the type of the message
        if (in.readableBytes() < 8) return; // 意思是没有读全，如果小于8个字节，就不要处理 TCP 拆包和粘包顺序 你知道发过来有多长
        // TCP 协议不用字符串
        in.markReaderIndex(); // this method is important to mark the current index in the byte array
        int ordinal = in.readInt();
        MsgType type = MsgType.values()[ordinal];
        int length = in.readInt();

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        in.readBytes(bytes); // read the bytes and put it in the bytes array, and this is the msg info
        Msg msg = null;
        // use reflection, so we don't have to use the below. Class.forName("com.msb.tank.net." +msg.toString()  +"Msg")
        switch (type) {
            case TankJoin:
                msg = new TankJoinMsg();
                //msg.parse(bytes);
                //out.add(length); depends on how many value added to the out (list)
                //out.add(tankJoinMsg); // see this in the readChannel
                break;
            case TankStartMoving:
                msg = new TankStartMovingMsg();
                break;
            case TankStop:
                msg = new TankStopMsg();
                break;
                //msg.parse(bytes);
                //tankStartMovingMsg.parse(bytes);
                //out.add(tankStartMovingMsg);
            case TankDirChanged:
                msg = new TankDirChangeMsg();
                break;
            case TankDie:
                msg = new TankDieMsg();
                break;
            case BulletNew:
                msg = new BulletNewMsg();
                break;
            default:
                break;
        }
        msg.parse(bytes);
        out.add(msg);
    }
}
