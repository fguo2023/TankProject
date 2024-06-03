package com.msb.tank.state.thread;

public class TerminatedState extends ThreadState_{
    private Thread_ t;

    public TerminatedState(Thread_ t) {
        this.t = t;
    }

    @Override
    void move(Action input) {
        if(input.msg == "running") t.state = new NewState(t);
    }

    @Override
    void run() {

    }
}
