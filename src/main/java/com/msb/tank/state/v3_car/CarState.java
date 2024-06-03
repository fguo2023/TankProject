package com.msb.tank.state.v3_car;

public abstract class CarState {
    abstract void open();
    abstract void closed();
    abstract void running();
    abstract void stopped();
}
