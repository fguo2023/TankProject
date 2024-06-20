import com.msb.tank.DIR;
import com.msb.tank.net.MsgEncoder;
import com.msb.tank.net.MsgType;
import com.msb.tank.net.TankStartMovingMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestTankStartMovingMsgCodec {
    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new MsgEncoder());
        UUID id = UUID.randomUUID();
        TankStartMovingMsg msg = new TankStartMovingMsg(id, 10, 5, DIR.DOWN);
        channel.writeOutbound(msg);

        ByteBuf buf = (ByteBuf) channel.readOutbound(); // receive the outbound message
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankStartMoving, msgType);

        int length = buf.readInt();
        assertEquals(msg.toBytes().length, length);
        long most = buf.readLong();
        long least = buf.readLong();
        assertEquals(most, id.getMostSignificantBits());
        assertEquals(least, id.getLeastSignificantBits());
        int x = buf.readInt();
        int y = buf.readInt();
        DIR dir = DIR.values()[buf.readInt()];
        assertEquals(x, 10);
        assertEquals(y, 5);
        assertEquals(dir, DIR.DOWN);
    }

    @Test
    public void testDecoder() {

    }

    @Test
    public void testEnum() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MsgType msgType = MsgType.TankStartMoving;
        System.out.println(msgType.toString());
        Class<?> clazz = Class.forName("com.msb.tank.net.TankStartMovingMsg");
        System.out.println(clazz.getName());
        Object o = clazz.getDeclaredConstructor().newInstance();
    }
}
