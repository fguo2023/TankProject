package com.msb.tank;

import java.awt.*;

public class Explode {
    private int x, y;
    public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private int step = 0;
    private TankFrame tf = null;

    public Explode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explodes[step++ % ResourceMgr.explodes.length], x, y, null);
        if (step >= ResourceMgr.explodes.length)
            tf.explodes.remove(this);
    }
}