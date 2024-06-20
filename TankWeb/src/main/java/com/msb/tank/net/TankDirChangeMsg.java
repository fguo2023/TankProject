package com.msb.tank.net;

import com.msb.tank.DIR;
import com.msb.tank.Tank;
import com.msb.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankDirChangeMsg extends Msg {
    int x, y;
    UUID id;
    DIR dir;

    public TankDirChangeMsg(int x, int y, UUID id, DIR dir) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.dir = dir;
    }

    public TankDirChangeMsg() {
    }

    public TankDirChangeMsg(Tank t) {
        this.x = t.getX();
        this.y = t.getY();
        this.id = t.getId();
        this.dir = t.getDir();
    }

    @Override
    public void handle() {
        // if send it by itself, then return
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId())) return;
        Tank t = TankFrame.INSTANCE.findTankByUUID(this.id);
        if (t != null) {
            t.setX(this.x);
            t.setY(this.y);
            t.setDir(this.dir);
        }
    }


    @Override
    public byte[] toBytes() {
        // encode the message and then pass through the channel
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(this.id.getMostSignificantBits());
            dos.writeLong(this.id.getLeastSignificantBits());
            dos.writeInt(this.x);
            dos.writeInt(this.y);
            dos.writeInt(dir.ordinal());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = DIR.values()[dis.readInt()];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(dis != null){
                try {
                    dis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDirChanged;
    }
}
