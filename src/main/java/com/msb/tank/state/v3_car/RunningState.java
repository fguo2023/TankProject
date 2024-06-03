package com.msb.tank.state.v3_car;

public class RunningState extends CarState {
    @Override
    void open() {

    }

    @Override
    void closed() {

    }

    @Override
    void running() {
        System.out.println("car running");
    }

    @Override
    void stopped() {
    }
}
