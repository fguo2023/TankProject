package com.msb.tank.strategy;

import com.msb.tank.Bullet;
import com.msb.tank.GameModel;
import com.msb.tank.Tank;
import com.msb.tank.decorator.RectDecorator;
import com.msb.tank.decorator.TailDecorator;

public class FireOneBullet implements FireStrategy {

//    private FireOneBullet(){}
//    private static final FireOneBullet INSTANCE = new FireOneBullet();
//
//    public static FireOneBullet getInstance() {
//        return INSTANCE;
//    }

    @Override
    public void fire(Tank tank) {
        int bX = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        // BUG? New Bullet add itself again
        //GameModel.getInstance().add(new RectDecorator(new TailDecorator(new Bullet(bX, bY, tank.getDir(), tank.getGroup()))));
        new Bullet(bX, bY, tank.getDir(), tank.getGroup());
    }
}
