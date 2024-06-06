package com.msb.tank;

import com.msb.tank.observertank.*;
import com.msb.tank.strategy.FireOneBullet;
import com.msb.tank.strategy.FireStrategy;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tank extends GameObject {
    private int oldX, oldY;
    private DIR dir = DIR.DOWN;
    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();
    private static final int SPEED = PropertyMgr.getIntValue(Constants.TANK_SPEED);
    private Random random = new Random();

    public Rectangle rect = new Rectangle();

    // private int speed =  SPEED;
    private Group group = Group.BAD;

    //public TankFrame tf = null;

    private boolean living = true;

    public Group getGroup() {
        return group;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private boolean moving = true;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public DIR getDir() {
        return dir;
    }

    public void setDir(DIR dir) {
        this.dir = dir;
    }

    FireStrategy fs = null;

    public Tank(int x, int y, DIR dir, Group group) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
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
        GameModel.getInstance().add(this);
        //this.tf = tf;
    }

    @Override
    public void paint(Graphics g) {
        if (!living) GameModel.getInstance().remove(this);
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

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    private void move() {
        oldX = x;
        oldY = y;
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

    public void back() {
        x = oldX;
        y = oldY;
    }

    public void stop() {
        moving = false;
    }

    // can add the transient means doesn't need to serialize
    private List<TankFireObserver> fireObservers = Arrays.asList(new TankFireHandler(), new TankFireLogHandler()); // name may change in the future

    public void handleFireKey() {
        TankFireEvent event = new TankFireEvent(this);
        // when the control button click, then fire the event, event contains the tank prop, and then observer do the action with the event, event's source do the action.
        for (TankFireObserver o : fireObservers) { // now has only one observer, can be more than one observer
            o.actionOnFire(event);
        }
    }
}