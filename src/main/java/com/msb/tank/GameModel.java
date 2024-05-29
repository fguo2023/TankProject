package com.msb.tank;

import com.msb.tank.cor.ColliderChain;

import java.awt.*;
import java.util.ArrayList;

public class GameModel {
    Tank myTank;
    //    ArrayList<Tank> tanks = new ArrayList<>();
    //    ArrayList<Explode> explodes = new ArrayList<>();
    //    public ArrayList<Bullet> bullets = new ArrayList<>();

    //Collider collider = new BulletTankCollider();
    //Collider collider2 = new TankTankCollider();

    private static final GameModel INSTANCE = new GameModel();

    static {
        INSTANCE.init();
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    ColliderChain chain = new ColliderChain();
    ArrayList<GameObject> objects = new ArrayList<>();

    public void add(GameObject go) {
        this.objects.add(go);
    }

    public void remove(GameObject go) {
        this.objects.remove(go);
    }

    private void init() {
        myTank = new Tank(200, 400, DIR.UP, Group.GOOD);
        int initTankCount = PropertyMgr.getIntValue(Constants.INIT_TANK_COUNT);
        for (int i = 0; i < initTankCount; i++) {
            new Tank(50 + i * 100, 200, DIR.UP, Group.BAD);
        }
        add(new Wall(150, 150, 200, 50));
        add(new Wall(550, 150, 200, 50));
        add(new Wall(300, 300, 50, 200));
        add(new Wall(550, 300, 50, 200));
    }

    /*
    Facade pattern
     */
    private GameModel() {}

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("bullet count: " + bullets.size(), 10, 60);
//        g.drawString("enemy tanks count: " + tanks.size(), 10, 80);
//        g.drawString("explosion count: " + explodes.size(), 10, 100);
        g.setColor(c);
        myTank.paint(g);
        // use b.paint(g) will have the concurrent issue. since use the iterator will have the concurrent issue!!!!
//        for (int i = 0; i < tanks.size(); i++) {
//            tanks.get(i).paint(g);
//        }
//        for (int i = 0; i < bullets.size(); i++) {
//            bullets.get(i).paint(g);
//        }
//
//        for (int i = 0; i < explodes.size(); i++) {
//            explodes.get(i).paint(g);
//        }
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }
//        for(Iterator<Bullet> it = bullets.iterator(); it.hasNext();){
//            Bullet b = it.next();
//            if(!b.isLive()){
//                it.remove();
//            }
//        }
        // collision logic
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject o1 = objects.get(i);
                GameObject o2 = objects.get(j);
                //collider.collide(o1, o2);
                //collider2.collide(o1,o2);
                chain.collide(o1, o2);
            }
        }
//        for (int i = 0; i < bullets.size(); i++) {
//            for (int j = 0; j < tanks.size(); j++) {
//                bullets.get(i).collideWith(tanks.get(j));
//            }
//        }
    }

    public Tank getMainTank() {
        return myTank;
    }
}
