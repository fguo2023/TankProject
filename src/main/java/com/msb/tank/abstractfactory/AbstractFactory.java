package com.msb.tank.abstractfactory;

public abstract class AbstractFactory {
    public abstract Food createFood();
    public abstract Weapon createWeapon();
    public abstract Vehicle createVehicle();
}
