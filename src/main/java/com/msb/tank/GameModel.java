package com.msb.tank;

import java.awt.*;
import java.util.ArrayList;

public class GameModel {
    Tank myTank = new Tank(200, 400, DIR.UP, Group.GOOD, this);
    ArrayList<Tank> tanks = new ArrayList<>();
    ArrayList<Explode> explodes = new ArrayList<>();
    public ArrayList<Bullet> bullets = new ArrayList<>();

    public GameModel() {
        int initTankCount = PropertyMgr.getIntValue(Constants.INIT_TANK_COUNT);
        for (int i = 0; i < initTankCount; i++) {
            this.tanks.add(new Tank(50 + i * 80, 200, DIR.UP, Group.BAD, this));
        }
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("bullet count: " + bullets.size(), 10, 60);
        g.drawString("enemy tanks count: " + tanks.size(), 10, 80);
        g.drawString("explosion count: " + explodes.size(), 10, 100);
        g.setColor(c);
        myTank.paint(g);
        // use b.paint(g) will have the concurrent issue. since use the iterator will have the concurrent issue!!!!
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
//        for(Iterator<Bullet> it = bullets.iterator(); it.hasNext();){
//            Bullet b = it.next();
//            if(!b.isLive()){
//                it.remove();
//            }
//        }
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
        }
    }

    public Tank getMainTank() {
        return myTank;
    }
}
