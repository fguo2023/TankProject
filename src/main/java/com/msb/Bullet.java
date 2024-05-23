package com.msb;

import java.awt.*;

public class Bullet {
    private static final int SPEED = 10;
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    private boolean living = true;
    private DIR dir;
    private int x, y;
    private TankFrame tf;
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Bullet(int x, int y, DIR dir, Group group, TankFrame tf) {
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.group = group;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if(!living){
             tf.bullets.remove(this);
        }
        switch (dir){
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }
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
            living = false;
        }
    }
/*
when the bullet intersects with the tank, then
 */
    public void collideWith(Tank tank) {
        if(this.group == tank.getGroup()) return;

        //TODO: 用一个rect来记录子弹的位置
        Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
        if(rect1.intersects(rect2)){
            tank.die();
            this.die();
            tf.explodes.add(new Explode(x, y, tf));
        }
    }

    private void die() {
        this.living = false;
    }
}
