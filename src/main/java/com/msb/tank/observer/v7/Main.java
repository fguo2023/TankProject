package com.msb.tank.observer.v7;

import java.util.ArrayList;

public class Main {
    static class Child {
        ArrayList<Observer> observersList = new ArrayList<>();
        private boolean cry = false;

        {
            observersList.add(new Mum());
            observersList.add(new Dad());
        }

        /*
           event will always have the getSource() method to get the source that is being observed such as the Child class here
           observer(listener) only interact with the event, not the source, to define a source must be an event object, an event object is included in the event
         */
        public void wakeup(wakeupEvent event) {
            cry = true;
            for (Observer o : observersList) {
                o.action();
            }
        }
    }

    static class wakeupEvent extends Event<Child>{
        long timestamp;
        String loc;
        Child source;

        @Override
        public Child getSource() {
            return source;
        }

        public wakeupEvent(long timestamp, String loc, Child source) {
            this.timestamp = timestamp;
            this.loc = loc;
            this.source = source;
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
        c.wakeup(new wakeupEvent(System.currentTimeMillis(), "bed",c));
    }
}
