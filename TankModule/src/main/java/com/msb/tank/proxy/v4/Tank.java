package com.msb.tank.proxy.v4;

import java.util.Random;

/*
this is an example of static proxy
 */
public class Tank implements Movable {
    // tank 是被代理对象
    public static void main(String[] args) throws InterruptedException {
        // like decorator, but it is proxy, java classloader 双亲委派 -> proxy
        new TankLogProxy(new TankTimeProxy(new Tank())).move();
    }

    @Override
    public void move() {
        System.out.println("tank start to move");
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// tank time proxy
class TankTimeProxy implements Movable {
    Movable m;

    public TankTimeProxy(Movable m) {
        this.m = m;
    }

    @Override
    public void move() throws InterruptedException {
        long start = System.currentTimeMillis();
        m.move(); // this is the 被代理对象, 这里也就是tank
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

/*
Log proxy
 */
class TankLogProxy implements Movable {
    Movable m;

    public TankLogProxy(Movable m) {
        this.m = m;
    }

    @Override
    public void move() throws InterruptedException {
        System.out.println("log tank moving");
        m.move();
        System.out.println("stopped");
    }
}


interface Movable {
    void move() throws InterruptedException;
}
