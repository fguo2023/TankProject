import com.msb.tank.DIR;
import com.msb.tank.Group;
import com.msb.tank.net.TankJoinMsg;
import com.msb.tank.net.TankJoinMsgDecoder;
import com.msb.tank.net.TankJoinMsgEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class TestTankJoinMsg {
    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 10, DIR.DOWN, true, Group.GOOD, id);
        channel.pipeline().addLast(new TankJoinMsgEncoder());

        channel.writeOutbound(msg);

        ByteBuf buf = (ByteBuf) channel.readOutbound();

        int x = buf.readInt();
        int y = buf.readInt();
        DIR dir = DIR.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group group = Group.values()[buf.readInt()];
        long leastSig = buf.readLong();
        long mostSig = buf.readLong();
        Assert.assertEquals(x, 5);
        Assert.assertEquals(y, 10);
        Assert.assertEquals(dir, DIR.DOWN);
        Assert.assertEquals(moving, true);
        Assert.assertEquals(group, Group.GOOD);
        Assert.assertEquals(leastSig, id.getMostSignificantBits());
        Assert.assertEquals(mostSig, id.getLeastSignificantBits());
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new TankJoinMsgDecoder());
        // bytebuf coming back
        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 10, DIR.DOWN, true, Group.GOOD, id);

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg.toBytes());

        channel.writeInbound(buf.duplicate());

        TankJoinMsg msgR = (TankJoinMsg) channel.readInbound();

        Assert.assertEquals(msgR.x, 5);
        Assert.assertEquals(msgR.y, 10);
        Assert.assertEquals(msgR.dir, DIR.DOWN);
        Assert.assertEquals(msgR.moving, true);
        Assert.assertEquals(msgR.group, Group.GOOD);
    }
}
