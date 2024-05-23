package com.msb.tank.strategy;

import com.msb.tank.Tank;
import com.msb.tank.TankFrame;

import java.io.Serializable;

public interface FireStrategy extends Serializable {
    void fire(Tank tank, TankFrame tf);
}
