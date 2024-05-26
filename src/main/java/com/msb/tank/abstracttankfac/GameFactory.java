package com.msb.tank.abstracttankfac;

import com.msb.tank.DIR;
import com.msb.tank.Group;
import com.msb.tank.TankFrame;

public abstract class GameFactory {
    public abstract BaseTank createTank(int x, int y, DIR dir, int speed, Group group, TankFrame tf);

    public abstract BaseBullet createBullet(int x, int y, DIR dir, Group group, TankFrame tf);

    public abstract BaseExplode createExplode(int x, int y, TankFrame tf);
}
