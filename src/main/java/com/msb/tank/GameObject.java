package com.msb.tank;

import java.awt.*;

// e.g. wall or any game object will inherit from this class
public abstract class GameObject {
    protected int x,y;
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void paint(Graphics g);
    public abstract int getWidth();
    public abstract int getHeight();

}
