package com.msb.nettystudy.s08;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TankMsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 8) return; // 意思是没有读全，如果小于8个字节，就不要处理 TCP 拆包和粘包顺序

        in.markReaderIndex();

        int x = in.readInt();
        int y = in.readInt();
        out.add(new TankMsg(x,y));
    }
}
