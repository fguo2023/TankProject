package com.msb.tank.cor;

import com.msb.tank.GameObject;
import com.msb.tank.Tank;

public class TankTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Tank && o2 instanceof Tank) {
            Tank tank = (Tank) o1;
            Tank tank1 = (Tank) o2;
            if (tank.rect.intersects(tank1.rect)) {
                tank.back();
                tank1.back();
                //tank1.stop();
            }
        }
        return true;
    }
}
