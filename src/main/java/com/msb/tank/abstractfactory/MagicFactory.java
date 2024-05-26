package com.msb.tank.abstractfactory;

public class MagicFactory extends AbstractFactory{
    @Override
    public Food createFood() {
        return new Mushroom();
    }

    @Override
    public Weapon createWeapon() {
        return new MagicStick();
    }

    @Override
    public Vehicle createVehicle() {
        return new Broom();
    }
}
