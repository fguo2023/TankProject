import com.msb.tank.DIR;
import com.msb.tank.Group;
import com.msb.tank.net.MsgType;
import com.msb.tank.net.TankJoinMsg;
import com.msb.tank.net.MsgDecoder;
import com.msb.tank.net.MsgEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.*;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;

public class TestTankJoinMsg {
    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 10, DIR.DOWN, true, Group.GOOD, id);
        channel.pipeline().addLast(new MsgEncoder());

        // after writeOutbound, the msg will pass through the TankJoinMsgEncoder (write out) channel -->
        channel.writeOutbound(msg);

        ByteBuf buf = (ByteBuf) channel.readOutbound(); // receive the outbound message
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankJoin, msgType);

        int length = buf.readInt();
        assertEquals(msg.toBytes().length, length);

        int x = buf.readInt();
        int y = buf.readInt();
        DIR dir = DIR.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group group = Group.values()[buf.readInt()];
        long leastSig = buf.readLong();
        long mostSig = buf.readLong();
        assertEquals(x, 5);
        assertEquals(y, 10);
        assertEquals(dir, DIR.DOWN);
        assertEquals(moving, true);
        assertEquals(group, Group.GOOD);
        assertEquals(leastSig, id.getMostSignificantBits());
        assertEquals(mostSig, id.getLeastSignificantBits());
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());
        // bytebuf coming back
        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 10, DIR.DOWN, true, Group.GOOD, id);

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankJoin.ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(msg.toBytes()); // 模拟encoder做的事情, write to buf

        channel.writeInbound(buf.duplicate());
        //int len = channel.readInbound();
        TankJoinMsg msgR = (TankJoinMsg) channel.readInbound();
       // assertEquals(len, bytes.length);
        assertEquals(msgR.x, 5);
        assertEquals(msgR.y, 10);
        assertEquals(msgR.dir, DIR.DOWN);
        assertEquals(msgR.moving, true);
        assertEquals(msgR.group, Group.GOOD);
    }

    @Test
    public void testByteBuf(){
        ByteBuf buf = Unpooled.buffer();
//        buf.writeInt(16);
//        buf.writeInt(26);
//        buf.writeBoolean(true);
        buf.writeBytes("hello".getBytes());

//        int one = buf.readInt();
//        buf.markReaderIndex();
        int leftBytes = buf.readableBytes();
        System.out.println(leftBytes);
        byte[] array = new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), array);
        System.out.println(Arrays.toString(array));
    }
}
