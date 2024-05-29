package com.msb.tank.cor;

import com.msb.tank.GameObject;
import com.msb.tank.Tank;
import com.msb.tank.Wall;

public class WallTankCollider implements Collider{
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Wall && o2 instanceof Tank) {
            Wall wall = (Wall) o1;
            Tank tank = (Tank) o2;
            if (tank.rect.intersects(wall.rect)) {
                tank.back();
            }
        } else if (o1 instanceof Tank && o2 instanceof Wall) {
            collide(o2, o1);
        }
        return true;
    }
}
