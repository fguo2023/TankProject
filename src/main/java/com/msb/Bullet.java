package com.msb;

import java.awt.*;

public class Bullet {
    private static final int SPEED = 10;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private boolean live = true;
    private DIR dir;
    private int x, y;
    private TankFrame tf;

    public Bullet(int x, int y, DIR dir, TankFrame tf) {
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if(!live){
             tf.bullets.remove(this);
        }
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        move();
    }

    private void move() {
        switch (dir) {
            case DOWN:
                y -= SPEED;
                break;
            case UP:
                y += SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case LEFT:
                x -= SPEED;
                break;
        }

        if(x < 0 || y < 0 || x >= TankFrame.GAME_WIDTH || y >= TankFrame.GAME_HEIGHT){
            live = false;
        }
    }
}
