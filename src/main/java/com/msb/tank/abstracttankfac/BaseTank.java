package com.msb.tank.abstracttankfac;

import com.msb.tank.Group;

import java.awt.*;

public abstract class BaseTank {

    private int x, y;
    public Rectangle rect = new Rectangle();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract void paint(Graphics g);

    private Group group = Group.BAD;

    public Group getGroup() {
        return group;
    }

    public abstract void die();
}
