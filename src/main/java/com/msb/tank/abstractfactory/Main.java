package com.msb.tank.abstractfactory;

public class Main {
    public static void main(String[] args) {
        AbstractFactory factory = new ModernFactory();
        Food food = factory.createFood();

        //...

    }
}
