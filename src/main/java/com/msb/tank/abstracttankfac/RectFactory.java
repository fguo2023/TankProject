package com.msb.tank.abstracttankfac;

import com.msb.tank.DIR;
import com.msb.tank.Group;
import com.msb.tank.TankFrame;

public class RectFactory extends GameFactory{
    @Override
    public BaseTank createTank(int x, int y, DIR dir, int speed, Group group, TankFrame tf) {
        return new RectTank(x,y, dir, speed,group,tf);
    }

    @Override
    public BaseBullet createBullet(int x, int y, DIR dir, Group group, TankFrame tf) {
        return new RectBullet(x, y, dir, group, tf);
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tf) {
        return new RectExplode(x,y, tf);
    }
}
