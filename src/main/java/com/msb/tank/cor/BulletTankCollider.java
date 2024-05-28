package com.msb.tank.cor;

import com.msb.tank.Bullet;
import com.msb.tank.GameObject;
import com.msb.tank.Tank;

public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Bullet) {
            Tank tank = (Tank) o1;
            Bullet bullet = (Bullet) o2;
            //TODO copy code from method collideWith
            if(bullet.collideWith(tank)){
                return false;
            }
        } else if (o1 instanceof Bullet && o2 instanceof Tank) {
            return collide(o2, o1);
        }
        return true;
    }
}
