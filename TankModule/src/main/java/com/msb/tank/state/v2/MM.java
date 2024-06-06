package com.msb.tank.state.v2;

public class MM {
    String name;
    MMState state;

    public void smile() {
        state = new MMHappyState();
        state.smile();
    }

    public void cry() {
        state = new MMHappyState();
        state.cry();
    }

    public void say() {
        state = new MMHappyState();
        state.say();
    }
}
