package com.msb.tank.cor;

import com.msb.tank.Bullet;
import com.msb.tank.Explode;
import com.msb.tank.GameObject;
import com.msb.tank.Wall;

public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Wall && o2 instanceof Bullet) {
            Wall wall = (Wall) o1;
            Bullet bullet = (Bullet) o2;
            if (bullet.getRect().intersects(wall.rect)) {
                bullet.die();
                // explosion
                bullet.gm.add(new Explode(wall.getX(), wall.getY(), bullet.gm));
            }
        } else if (o1 instanceof Bullet && o2 instanceof Wall) {
            collide(o2, o1);
        }
        return true;
    }
}
