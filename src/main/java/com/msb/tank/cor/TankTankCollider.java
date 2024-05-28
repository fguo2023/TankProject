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
                tank.setX(tank.getOldX());
                tank.setY(tank.getOldY());
                tank1.setX(tank1.getOldX());
                tank1.setY(tank1.getOldY());
                //tank1.stop();
            }
        }
        return true;
    }
}
