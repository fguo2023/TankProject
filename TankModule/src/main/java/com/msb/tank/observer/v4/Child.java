package com.msb.tank.observer.v4;

import java.util.ArrayList;

public class Child {
    ArrayList<Observer> observersList = new ArrayList<>();
    private boolean cry = false;
    {
        observersList.add(new Mum());
        observersList.add(new Dad());
    }

    public void wakeup() {
        cry = true;
        for (Observer o : observersList) {
            o.action();
        }
    }
}
