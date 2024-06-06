package com.msb.tank.observer.v5;

import java.util.ArrayList;

public class Main {
    static class Child {
        ArrayList<Observer> observersList = new ArrayList<>();
        private boolean cry = false;

        {
            observersList.add(new Mum());
            observersList.add(new Dad());
        }

        public void wakeup(wakeupEvent event) {
            cry = true;
            wakeupEvent wakeupEvent = new wakeupEvent(System.currentTimeMillis(), "bed");
            for (Observer o : observersList) {
                o.action();
            }
        }
    }

    static class wakeupEvent {
        long timestamp;
        String loc;

        public wakeupEvent(long timestamp, String loc) {
            this.timestamp = timestamp;
            this.loc = loc;
        }
    }

    interface Observer {
        void action();
    }

    static class Dad implements Observer {
        public void action() {
            System.out.println("dad feeding");
        }
    }

    static class Mum implements Observer {
        public void action() {
            System.out.println("mum hugging");
        }
    }

    public static void main(String[] args) {
        Child c = new Child();
        c.wakeup(new wakeupEvent(System.currentTimeMillis(), "bed"));
    }
}
