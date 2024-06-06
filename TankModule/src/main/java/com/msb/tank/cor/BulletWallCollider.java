package com.msb.tank.cor;

import com.msb.tank.*;

public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Wall && o2 instanceof Bullet) {
            Wall wall = (Wall) o1;
            Bullet bullet = (Bullet) o2;
            if (bullet.getRect().intersects(wall.rect)) {
                bullet.die();
                // explosion
                int ex = bullet.getX();
                int ey = bullet.getY();
                new Explode(ex, ey); // the explosion will add to the GameModel since in the Explode class each time when it new the object, will add to the GameModel
            }
        } else if (o1 instanceof Bullet && o2 instanceof Wall) {
            collide(o2, o1);
        }
        return true;
    }
}
