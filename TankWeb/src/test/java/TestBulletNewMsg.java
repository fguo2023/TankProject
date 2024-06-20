import com.msb.tank.Bullet;
import com.msb.tank.DIR;
import com.msb.tank.Group;
import com.msb.tank.net.BulletNewMsg;
import com.msb.tank.net.MsgDecoder;
import com.msb.tank.net.MsgEncoder;
import com.msb.tank.net.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestBulletNewMsg {
    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        UUID playerId = UUID.randomUUID();
        Bullet bullet = new Bullet(playerId, 5, 10, DIR.DOWN, Group.GOOD, null);
        BulletNewMsg msg = new BulletNewMsg(bullet);
        channel.pipeline().addLast(new MsgEncoder());


        channel.writeOutbound(msg);

        ByteBuf buf = (ByteBuf) channel.readOutbound(); // receive the outbound message
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.BulletNew, msgType);

        int length = buf.readInt();
        assertEquals(msg.toBytes().length, length);

        UUID pid = new UUID(buf.readLong(), buf.readLong());
        assertEquals(playerId, pid);

        buf.readLong();
        buf.readLong();
        int x = buf.readInt();
        assertEquals(x, 5);

//        int x = buf.readInt();
//        int y = buf.readInt();
//        DIR dir = DIR.values()[buf.readInt()];
//        boolean moving = buf.readBoolean();
//        Group group = Group.values()[buf.readInt()];
//        long leastSig = buf.readLong();
//        long mostSig = buf.readLong();
//        assertEquals(x, 5);
//        assertEquals(y, 10);
//        assertEquals(dir, DIR.DOWN);
//        assertEquals(moving, true);
//        assertEquals(group, Group.GOOD);
//        assertEquals(leastSig, id.getMostSignificantBits());
//        assertEquals(mostSig, id.getLeastSignificantBits());

    }
    @Test
    public void testDecode(){
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgDecoder());

        UUID playerId = UUID.randomUUID();
        Bullet bullet = new Bullet(playerId, 5, 10, DIR.DOWN, Group.GOOD, null);
        BulletNewMsg msg = new BulletNewMsg(bullet);

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.BulletNew.ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(msg.toBytes()); //
        channel.writeInbound(buf.duplicate());

        BulletNewMsg bulletNewMsg = (BulletNewMsg) channel.readInbound();
        assertEquals(bulletNewMsg.getPlayerId(), playerId);
        assertEquals(bulletNewMsg.getDir(), DIR.DOWN);
        assertEquals(bulletNewMsg.getX(), 5);
    }
}
