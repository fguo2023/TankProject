package com.msb.tank.decorator;

import com.msb.tank.GameObject;

import java.awt.*;

public class TailDecorator extends GODecorator {
    public TailDecorator(GameObject go) {
        super(go);
    }

    @Override
    public void paint(Graphics g) {
        this.x = go.getX();
        this.y = go.getY();
        go.paint(g);
        Color c = g.getColor();
        g.setColor(Color.BLUE);
        g.drawLine(go.getX(), go.getY(), go.getX() + getWidth(), go.getY() + getHeight());
        g.setColor(c);
    }

    @Override
    public int getWidth() {
        return super.go.getWidth();
    }

    @Override
    public int getHeight() {
        return super.go.getHeight();
    }
}
