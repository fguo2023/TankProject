package com.msb.tank;

//Tank will have two subsidiaries: Design Pattern and Network
// this is the dp branch
public class Main {
    public static final String INIT_TANK_COUNT = "initTankCount";

    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();
        // init enemy tanks
        int initTankCount = PropertyMgr.getIntValue(INIT_TANK_COUNT);
        for (int i = 0; i < initTankCount; i++) {
            tf.tanks.add(new Tank(50 + i * 80, 200, DIR.UP, 1, Group.BAD, tf));
        }

        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }
    }
}