package com.msb.tank;

import java.util.Properties;

//Tank will have two subsidiaries: Design Pattern and Network
// this is the dp branch
public class Main {


    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }
    }
}