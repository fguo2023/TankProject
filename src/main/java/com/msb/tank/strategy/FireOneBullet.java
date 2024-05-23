package com.msb.tank.strategy;

import com.msb.tank.Bullet;
import com.msb.tank.Tank;
import com.msb.tank.TankFrame;

public class FireOneBullet implements FireStrategy{
    @Override
    public void fire(Tank tank, TankFrame tf) {
        int bX = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        tf.bullets.add(new Bullet(bX, bY, tank.getDir(), tank.getGroup(),tf));
    }
}
