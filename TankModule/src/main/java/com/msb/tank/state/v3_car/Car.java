package com.msb.tank.state.v3_car;

public class Car {
    private CarState state;

    public static void main(String[] args) {
        Car car = new Car();
        car.openDoor();
        car.closeDoor();
    }

    public void openDoor() {
        state = new OpenState();
        state.open();
    }

    public void closeDoor() {
        state = new ClosedState();
        state.closed();
    }

    public void runCar() {
        state = new RunningState();
        state.running();
    }

    public void stopCar() {
        state = new StoppedState();
        state.stopped();
    }
}
