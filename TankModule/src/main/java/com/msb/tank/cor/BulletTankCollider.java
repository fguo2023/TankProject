package com.msb.tank.cor;

import com.msb.tank.*;

public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Bullet) {
            Tank tank = (Tank) o1;
            Bullet bullet = (Bullet) o2;
            //TODO copy code from method collideWith
            if(bullet.getGroup() == tank.getGroup()) return true;
            if(bullet.getRect().intersects(tank.rect)){
                bullet.die();
                tank.die();
                int ex = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
                int ey = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
                GameModel.getInstance().add(new Explode(ex, ey));
                return false;
            }
//            if(bullet.collideWith(tank)){
//                return false;
//            }
        } else if (o1 instanceof Bullet && o2 instanceof Tank) {
            return collide(o2, o1);
        }
        return true;
    }
}
