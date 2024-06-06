package com.msb.tank.state.v3_car;

public class OpenState extends CarState{
    @Override
    void open() {
        System.out.println("car open");
    }

    @Override
    void closed() {

    }

    @Override
    void running() {
    }

    @Override
    void stopped() {

    }
}
