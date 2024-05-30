package com.msb.tank.decorator;

import com.msb.tank.GameObject;

import java.awt.*;
import java.io.Reader;

/*
 See the Reader since under the Reader class, compose the inputStream
 Writer : outputStream
 */
public abstract class GODecorator extends GameObject {
    GameObject go;

    public GODecorator(GameObject go) {
        this.go = go;
    }

    public abstract void paint(Graphics g);
}
