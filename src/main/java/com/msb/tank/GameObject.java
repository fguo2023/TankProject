package com.msb.tank;

import java.awt.*;

// e.g. wall or any game object will inherit from this class
public abstract class GameObject {
    int x,y;

    public abstract void paint(Graphics g);

}
