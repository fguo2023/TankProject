package com.msb.tank.decorator;

import com.msb.tank.GameObject;

import java.awt.*;

public class RectDecorator extends GODecorator {
    public RectDecorator(GameObject go) {
        super(go);
    }

    @Override
    public void paint(Graphics g) {
        this.x = go.getX();
        this.y = go.getY();
        go.paint(g);
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawRect(go.getX(), go.getY(), go.getWidth(), go.getHeight());
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
