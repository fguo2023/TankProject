package com.msb.tank.abstractfactory;

import com.msb.tank.factorymethod.Moveable;

public class Car extends Vehicle {
    @Override
    public void go() {
        System.out.println("car go wuwuwuwu...");
    }
}
