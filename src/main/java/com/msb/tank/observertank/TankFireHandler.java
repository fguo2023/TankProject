package com.msb.tank.observertank;

import com.msb.tank.Tank;

public class TankFireHandler implements TankFireObserver{
    @Override
    public void actionOnFire(TankFireEvent event) {
        Tank t = event.getSource();
        t.fire();
    }
}
