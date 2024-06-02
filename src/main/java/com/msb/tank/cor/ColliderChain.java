package com.msb.tank.cor;

import com.msb.tank.Constants;
import com.msb.tank.GameObject;
import com.msb.tank.PropertyMgr;

import java.io.Serializable;
import java.util.LinkedList;

public class ColliderChain implements Collider {
    public LinkedList<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        // add colliders
        String colliderNames = (String) PropertyMgr.get(Constants.COLLIERS);
        if (colliderNames != null) {
            String[] clazzNames = colliderNames.split(",");
            for (int i = 0; i < clazzNames.length; i++) {
                try {
                    // add all the colliders from the config files to the chain
                    String clazzName = clazzNames[i];
                    Collider c = (Collider) Class.forName(clazzName).getDeclaredConstructor().newInstance();
                    add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        //add(new BulletTankCollider());
        //add(new TankTankCollider());
    }

    public void add(Collider c) {
        colliders.add(c);
    }

    public boolean collide(GameObject o1, GameObject o2) {
        // understand the logic here all the colliders call collide each other
        for (int i = 0; i < colliders.size(); i++) {
            if (!colliders.get(i).collide(o1, o2)) {
                return false;
            }
        }
        return true;
    }
}
