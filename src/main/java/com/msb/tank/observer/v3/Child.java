package com.msb.tank.observer.v3;

public class Child {
    public boolean isCry() {
        return cry;
    }

    private boolean cry = false; // use a boolean to see whether listener is calling
    private Dad dad = new Dad();
    private Mum mum = new Mum();

    public Child(){}
    public void wakeup(){
        cry = true;
        dad.feed();
        mum.hug();
    }
}
