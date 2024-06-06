package com.msb.tank.observer.v7;

import java.util.ArrayList;

public class Main {
    static class Child {
        ArrayList<Observer> observers = new ArrayList<>();
        private boolean cry = false;

        {
            observers.add(new Mum());
            observers.add(new Dad());
            //  hook callback function is the observer 钩子函数， callback就是观察者模式
            observers.add((e)-> System.out.println("ppp"));
        }

        /*
           event will always have the getSource() method to get the source that is being observed such as the Child class here
           observer(listener) only interact with the event, not the source, to define a source must be an event object, an event object is included in the event
         */
        public void wakeup(wakeupEvent event) {
            cry = true;
            for (Observer o : observers) {
                o.action(event);
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
        void action(Event e);
    }

    static class Dad implements Observer {
        @Override
        public void action(Event e) {

        }
    }

    static class Mum implements Observer {
        public void action(Event e) {
            System.out.println("mum hugging");
        }
    }

    public static void main(String[] args) {
        Child c = new Child();
        c.wakeup(new wakeupEvent(System.currentTimeMillis(), "bed",c));
    }
}
