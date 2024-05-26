package com.msb.tank.strategy;

import com.msb.tank.Bullet;
import com.msb.tank.DIR;
import com.msb.tank.Tank;
import com.msb.tank.TankFrame;
import com.msb.tank.abstracttankfac.GameFactory;

public class FireFourDirection implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        int bX = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        DIR[] dirs = DIR.values();
        for (DIR dir : dirs) {
            tank.tf.gameFactory.createBullet(bX, bY, dir, tank.getGroup(), tank.tf);
        }
    }
}
