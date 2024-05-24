package com.msb.tank.strategy;

import com.msb.tank.Bullet;
import com.msb.tank.Tank;

public class FireOneBullet implements FireStrategy {

//    private FireOneBullet(){}
//    private static final FireOneBullet INSTANCE = new FireOneBullet();
//
//    public static FireOneBullet getInstance() {
//        return INSTANCE;
//    }

    @Override
    public void fire(Tank tank) {
        int bX = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        new Bullet(bX, bY, tank.getDir(), tank.getGroup(), tank.tf);
    }
}
