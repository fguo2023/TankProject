package com.msb.tank;

import java.awt.*;

public class Explode extends GameObject {
    public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private int step = 0;
    private TankFrame tf = null;
    //private GameModel gm;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        //this.gm = gm;
        GameModel.getInstance().add(this);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explodes[step++ % ResourceMgr.explodes.length], x, y, null);
        if (step >= ResourceMgr.explodes.length)
            GameModel.getInstance().remove(this);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}