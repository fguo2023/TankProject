package com.msb.tank.observertank;

import com.msb.tank.Tank;

public class TankFireEvent {
    private Tank tank;

    public Tank getSource(){
        return tank;
    }

    public TankFireEvent(Tank tank) {
        this.tank = tank;
    }
}
