package com.msb.tank.cor;

import com.msb.tank.GameObject;

public interface Collider {
    boolean collide(GameObject o1, GameObject o2);
}
