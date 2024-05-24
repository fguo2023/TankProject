package com.msb.tank;

import com.msb.tank.strategy.FireOneBullet;
import com.msb.tank.strategy.FireStrategy;
import com.sun.xml.internal.bind.v2.TODO;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Tank {
    private int x, y;
    private DIR dir = DIR.DOWN;
    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();
    private static final int SPEED = PropertyMgr.getIntValue(Constants.TANK_SPEED);
    private Random random = new Random();

    Rectangle rect = new Rectangle();

    // private int speed =  SPEED;
    private Group group = Group.BAD;

    public TankFrame tf = null;

    private boolean living = true;

    public boolean isMoving() {
        return moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private boolean moving = true;

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

    FireStrategy fs = null;

    public Tank(int x, int y, DIR dir, int speed, Group group, TankFrame tf) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        if (group == Group.GOOD) {
            String fireFourDirection = (String) PropertyMgr.get(Constants.FIRE_FOUR_DIRECTION);
            try {
                fs = (FireStrategy) Class.forName(fireFourDirection).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
           /* use lambda expression is fine
                    fs = (tank) -> {
                int bX = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
                int bY = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
                new Bullet(bX, bY, tank.getDir(), tank.getGroup(), tank.tf);
            };
            or use fs = new FireOneBullet();*/
            fs = new FireOneBullet();
        }
        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if (!living) tf.tanks.remove(this);
        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
        }
        move();
    }

    private void move() {
        if (!moving) return;
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

        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            this.fire();
        }

        if (group == Group.BAD && random.nextInt(100) > 95)
            randomDir();

        boundsCheck();
        // update rect
        rect.x = this.x;
        rect.y = this.y;
    }

    private void boundsCheck() {
        if (this.x < 0) x = 0;
        if (this.y < 30) y = 30;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH) x = TankFrame.GAME_WIDTH - Tank.WIDTH;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
    }

    private void randomDir() {
        this.dir = DIR.values()[random.nextInt(4)];
    }

    public void fire() {
        fs.fire(this);
    }

    public void die() {
        this.living = false;
    }
}