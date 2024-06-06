package com.msb.tank.cor;

import com.msb.tank.GameObject;

import java.io.Serializable;

public interface Collider extends Serializable {
    boolean collide(GameObject o1, GameObject o2);
}
