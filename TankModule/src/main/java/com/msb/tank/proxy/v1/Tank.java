package com.msb.tank.proxy.v1;

import java.util.Random;

public class Tank implements Movable{

    public static void main(String[] args) {
        new Tank().move();
    }
    @Override
    public void move() {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}

interface Movable{
    void move() throws InterruptedException;
}
