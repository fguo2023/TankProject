package com.msb.tank;

import com.msb.tank.net.Client;

// TODO: GAME_WITH and GAME_HEIGHT not able to read from the main thread
public class Main {
    public static final String INIT_TANK_COUNT = "initTankCount";
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = TankFrame.INSTANCE;
        // init enemy tanks
//        int initTankCount = PropertyMgr.getIntValue(INIT_TANK_COUNT);
//        for (int i = 0; i < initTankCount; i++) {
//            tf.tanks.add(new Tank(50 + i * 80, 200, DIR.UP, 1, Group.BAD, tf));
//        }
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(25);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        Client client = new Client();
        client.connect();
    }
}