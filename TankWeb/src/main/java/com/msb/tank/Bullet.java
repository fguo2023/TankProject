package com.msb.tank;

import com.msb.tank.net.Client;
import com.msb.tank.net.TankDieMsg;

import java.awt.*;
import java.util.UUID;

public class Bullet {
    private static final int SPEED = PropertyMgr.getIntValue(Constants.BULLET_SPEED);
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    Rectangle rect = new Rectangle();

    private UUID id;
    private UUID playerId;

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public DIR getDir() {
        return dir;
    }

    public void setDir(DIR dir) {
        this.dir = dir;
    }

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

    public TankFrame getTf() {
        return tf;
    }

    public void setTf(TankFrame tf) {
        this.tf = tf;
    }

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public Bullet(UUID playerId, int x, int y, DIR dir, Group group, TankFrame tf) {
        this.id = UUID.randomUUID();
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }


    public void paint(Graphics g) {
        if (!living) {
            tf.bullets.remove(this);
        }
        switch (dir) {
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

        if (x < 0 || y < 0 || x >= TankFrame.GAME_WIDTH || y >= TankFrame.GAME_HEIGHT) {
            living = false;
        }
    }

    /*
    when the bullet intersects with the tank, then
     */
    public void collideWith(Tank tank) {
        //if(this.group == tank.getGroup()) return;
        if (this.playerId.equals(tank.getId())) return;
        if (this.living && tank.isLiving() && this.rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            Client.INSTANCE.send(new TankDieMsg(this.id, tank.getId()));
        }
        //TODO: 用一个rect来记录子弹的位置
        // Rectangle rect1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        // Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), Tank.WIDTH, Tank.HEIGHT);
//        if(rect.intersects(tank.rect)){
//            tank.die();
//            this.die(); // this is the bullet
//            int ex = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
//            int ey = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
//            tf.explodes.add(new Explode(ex, ey, tf));
//        }
    }

    public void die() {
        this.living = false;
    }
}
