package com.msb.tank.factorymethod;

import com.msb.tank.factorymethod.simplefactory.SimpleVehicleFactory;

public class PlaneFactory extends SimpleVehicleFactory {
    @Override
    public Moveable create() {
        return new Plane();
    }
}
