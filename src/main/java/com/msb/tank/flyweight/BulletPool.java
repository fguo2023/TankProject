package com.msb.tank.flyweight;

import java.util.ArrayList;
import java.util.UUID;

/**
 * use the poolish thinking 池化思想，重复利用
 */
class Bullet {
    public UUID id = UUID.randomUUID();
    boolean living = false;

    @Override
    public String toString() {
        return "Bullet{" +
                "id=" + id +
                ", living=" + living +
                '}';
    }
}

public class BulletPool {

    ArrayList<Bullet> bullets = new ArrayList<>();

    {
        for (int i = 0; i < 5; i++) {
            bullets.add(new Bullet());
        }
    }

    public Bullet getBullet() {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).living) return bullets.get(i);
        }
        return new Bullet();
    }

    public static void main(String[] args) {
         BulletPool bp = new BulletPool();
        for (int i = 0; i < 10; i++) {
            Bullet b = bp.getBullet();
            System.out.println(b);
        }
    }

}
