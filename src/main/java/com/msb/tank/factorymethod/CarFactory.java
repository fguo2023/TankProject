package com.msb.tank.factorymethod;

import com.msb.tank.factorymethod.simplefactory.SimpleVehicleFactory;

public class CarFactory extends SimpleVehicleFactory {
    @Override
    public Moveable create() {
        return new Car();
    }
//    public Car create(){
//        // before processing logging
//        System.out.println("a car created");
//        return new Car();
//    }
}
