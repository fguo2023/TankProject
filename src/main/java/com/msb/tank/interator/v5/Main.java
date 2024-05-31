package com.msb.tank.interator.v5;


public class Main {
    public static void main(String[] args) {

        Collection_<String> list = new LinkedList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new String("s" + i));
        }

        Iterator_<String> it = list.iterator();
        while (it.hasNext()) {
            String o =  it.next();
            System.out.println(o);
        }

        System.out.println(list.size());
    }
}
