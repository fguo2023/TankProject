package com.msb.tank.state.thread;

public class Thread_ {
    protected ThreadState_ state;

    public Thread_(ThreadState_ state) {
        this.state = state;
    }

    void move(Action input){
        state.move(input);
    }
    void runt(){state.run();}

}
