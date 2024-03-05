package com.msb;

import java.awt.*;

public class Tank {
    private int x, y;
    private DIR dir = DIR.DOWN;
    public static final int WIDTH = ResourceMgr.tankD.getWidth();
    public static final int HEIGHT = ResourceMgr.tankD.getHeight();
    private static final int SPEED = 5;

    private TankFrame tf = null;

    private boolean living = true;

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
        if(!living) tf.tanks.remove(this);
        switch (dir){
            case LEFT:
                g.drawImage(ResourceMgr.tankL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tankR, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.tankD, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tankU, x, y, null);
                break;
        }
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
        int bX = this.x + Tank.WIDTH /2 - Bullet.WIDTH/2;
        int bY = this.y + Tank.HEIGHT /2 - Bullet.HEIGHT/2;
        tf.bullets.add(new Bullet(bX, bY, this.dir, this.tf));
    }

    public void die() {
        this.living = false;
    }
}