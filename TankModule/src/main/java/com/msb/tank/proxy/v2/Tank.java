package com.msb.tank.proxy.v2;

import java.util.Random;

public class Tank implements Movable{

    public static void main(String[] args) throws InterruptedException {
        new TankTimeProxy(new Tank()).move();
    }
    @Override
    public void move() {
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// this use the composite pattern to implement
class TankTimeProxy implements Movable{
    Tank tank;

    public TankTimeProxy(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void move() throws InterruptedException {
        long start = System.currentTimeMillis();
        tank.move();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}


interface Movable{
    void move() throws InterruptedException;
}
