package com.msb.tank;

import java.awt.*;

public class Bullet {
    private static final int SPEED = PropertyMgr.getIntValue(Constants.BULLET_SPEED);;
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    Rectangle rect = new Rectangle();

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
        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
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

        rect.x = this.x;
        rect.y = this.y;

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
        // Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        // Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
        if(rect.intersects(tank.rect)){
            tank.die();
            this.die();
            int ex = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
            int ey = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
            tf.explodes.add(new Explode(ex, ey, tf));
        }
    }

    private void die() {
        this.living = false;
    }
}
