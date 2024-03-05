package com.msb;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();
        // init enemy tanks
        for (int i = 0; i < 5; i++) {
            tf.tanks.add(new Tank(50 + i * 80, 200, DIR.UP, Group.BAD, tf));
        }
        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }
    }
}