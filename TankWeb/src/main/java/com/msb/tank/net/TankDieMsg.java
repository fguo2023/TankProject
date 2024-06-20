package com.msb.tank.net;

import com.msb.tank.Bullet;
import com.msb.tank.Tank;
import com.msb.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankDieMsg extends Msg {
    UUID id;
    UUID bulletId;

    public TankDieMsg(UUID id, UUID bulletId) {
        this.id = id;
        this.bulletId = bulletId;
    }

    public TankDieMsg() {
    }

    @Override
    public String toString() {
        return "TankDieMsg{" +
                "id=" + id +
                ", bulletId=" + bulletId +
                '}';
    }

    @Override
    public void handle() {
        System.out.println("we got a tank die: " + id);
        System.out.println("and my tank is " + TankFrame.INSTANCE.getMainTank().getId());

        Tank tt = TankFrame.INSTANCE.findTankByUUID(id);
        System.out.println("i found a tank with id: " + tt);
        Bullet b = TankFrame.INSTANCE.findBulletByUUID(bulletId);
        if(b != null){
            b.die();
        }
        if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId())){
            TankFrame.INSTANCE.getMainTank().die();
        }else{
            Tank t = TankFrame.INSTANCE.findTankByUUID(id);
            if(t != null){
                t.die();
            }
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());
            dos.writeLong(id.getMostSignificantBits());
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

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.bulletId= new UUID(dis.readLong(), dis.readLong());
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
        return MsgType.TankDie;
    }
}
