package com.msb.tank.ASM;

public class T1 {
    int i = 0;
    public void m(){
        int j = 1;
        int k = 0;
        int a = ++k;
        System.out.println(a);
    }

    public static void main(String[] args) {
        T1 t = new T1();
        t.m();
    }
}
