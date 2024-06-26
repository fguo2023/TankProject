package com.msb.tank.net;

import com.msb.tank.DIR;
import com.msb.tank.Group;
import com.msb.tank.Tank;
import com.msb.tank.TankFrame;

import java.io.*;
import java.util.UUID;

// supposed the UUID should be created generated from the server, then the UUID will be the unique
public class TankJoinMsg extends Msg {
    public int x, y; // one int 4 bytes
    public DIR dir;
    public boolean moving;
    public Group group;
    public UUID id;

    public TankJoinMsg(Tank t) {
        this.x = t.getX();
        this.y = t.getY();
        this.dir = t.getDir();
        this.moving = t.isMoving();
        this.group = t.getGroup();
        this.id = t.getId();
    }

    public TankJoinMsg(int x, int y, DIR dir, boolean moving, Group group, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
    }

    public TankJoinMsg() {
    }

    @Override
    public void parse(byte[] bytes) {
        // use bytes to and convert to the TankJoinMsg
        // read the byte[] one by one that we need to use DataInputStream, first convert the bytes to the ByteArrayInputStream to be able to parse to the DataInputStream
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = DIR.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankJoin;
    }

    // 转换成字节数组
    // 也可以用ByteBuf 来写，但是如果将来不用netty，就得重写
    // 用数字往外传都是4个字节，如果都用字符串往外传效率低
    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving); // 1 byte to write out
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits()); // get the 64 bits, UUID is 128 bits
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public DIR getDir() {
        return dir;
    }

    public void setDir(DIR dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
    }

    @Override
    public void handle() {
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId()) || TankFrame.INSTANCE.findTankByUUID(this.id) != null)
            return;
        System.out.println(this); // read the TankJoinMsg to th sever
        Tank t = new Tank(this);
        TankFrame.INSTANCE.addTank(t);
        // send a new TankJoinMsg to the new joined tank
        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }
}
