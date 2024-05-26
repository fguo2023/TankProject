package com.msb.tank.abstracttankfac;

import com.msb.tank.ResourceMgr;
import com.msb.tank.TankFrame;

import java.awt.*;

public class RectExplode extends BaseExplode {
    private int x, y;
//    public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
//    public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private int step = 0;
    private TankFrame tf = null;

    public RectExplode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    @Override
    public void paint(Graphics g) {
        //g.drawImage(ResourceMgr.explodes[step++ % ResourceMgr.explodes.length], x, y, null);
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, 10 * step, 10 * step);
        step++;
        if (step >= 15)
            tf.explodes.remove(this);
        g.setColor(c);
    }
}
