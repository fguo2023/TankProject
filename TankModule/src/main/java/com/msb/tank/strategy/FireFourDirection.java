package com.msb.tank.strategy;

import com.msb.tank.Bullet;
import com.msb.tank.DIR;
import com.msb.tank.Tank;

public class FireFourDirection implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        int bX = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        DIR[] dirs = DIR.values();
        for (DIR dir : dirs) {
            new Bullet(bX, bY, dir, tank.getGroup());
        }
    }
}
