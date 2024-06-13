package com.msb.nettystudy.s08;

public class TankMsg {
    public int x,y; // one int 4 bytes

    public TankMsg(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "TankMsg{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
