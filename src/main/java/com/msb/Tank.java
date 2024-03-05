package com.msb;

import java.awt.*;

public class Tank {
    private int x, y;
    private DIR dir = DIR.DOWN;
    private static final int SPEED = 5;

    private TankFrame tf = null;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private boolean moving = false;

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

    public DIR getDir() {
        return dir;
    }

    public void setDir(DIR dir) {
        this.dir = dir;
    }

    public Tank(int x, int y, DIR dir,TankFrame tf) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }
   public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
       g.fillRect(x, y, 50, 50);
       g.setColor(c);
       move();
   }

    private void move() {
        if(!moving) return;
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
    }

    public void fire() {
        tf.bullets.add(new Bullet(this.x, this.y, this.dir, this.tf));
    }
}