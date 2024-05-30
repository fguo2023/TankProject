package com.msb.tank.observertank;

import com.msb.tank.Tank;

public class TankFireLogHandler implements TankFireObserver{

    @Override
    public void actionOnFire(TankFireEvent event) {
        Tank t = event.getSource();
        System.out.println("tank with height: " + t.getHeight() + " is firing!!");
    }
}
