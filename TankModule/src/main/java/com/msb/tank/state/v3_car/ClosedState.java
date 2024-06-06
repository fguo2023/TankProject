package com.msb.tank.state.v3_car;

public class ClosedState extends CarState{

    @Override
    void open() {
    }

    @Override
    void closed() {
        System.out.println("car closed");
    }

    @Override
    void running() {

    }

    @Override
    void stopped() {

    }
}
