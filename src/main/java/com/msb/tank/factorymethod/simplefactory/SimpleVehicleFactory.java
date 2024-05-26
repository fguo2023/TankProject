package com.msb.tank.factorymethod.simplefactory;

import com.msb.tank.factorymethod.Broom;
import com.msb.tank.factorymethod.Car;
import com.msb.tank.factorymethod.Moveable;

public abstract class SimpleVehicleFactory {

    public abstract  Moveable create();
//    public Moveable createCar(){
//        // before processing
//        return new Car();
//    }
//
//    public Moveable createBroom(){
//        return new Broom();
//    }
}
