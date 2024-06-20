package com.msb.tank;

import com.msb.tank.net.Client;
import com.msb.tank.net.TankDirChangeMsg;
import com.msb.tank.net.TankStartMovingMsg;
import com.msb.tank.net.TankStopMsg;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class TankFrame extends Frame {

    public static final TankFrame INSTANCE = new TankFrame();

    //static final int GAME_WIDTH = PropertyMgr.getIntValue(Constants.GAME_WIDTH);
    static final int GAME_WIDTH = 1200;
    //static final int GAME_HEIGHT = PropertyMgr.getIntValue(Constants.GAME_HEIGHT);
    static final int GAME_HEIGHT = 800;

    // ArrayList<Bullet> bullets = new ArrayList<>();

    Random r = new Random();
    private int myTankSpeed = PropertyMgr.getIntValue(Constants.TANK_SPEED);
    Tank myTank = new Tank(r.nextInt(GAME_WIDTH), r.nextInt(GAME_HEIGHT), DIR.UP, myTankSpeed, Group.GOOD, this);
    Map<UUID, Tank> tanks = new HashMap<>();
    List<Bullet> bullets = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();

    public Tank getMainTank() {
        return this.myTank;
    }

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");
        setVisible(true);
        this.addKeyListener(new MyKeyListener());
        addWindowListener(new WindowAdapter() { // the X on the window
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // 使用双缓冲解决闪烁问题
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics goffScreen = offScreenImage.getGraphics();
        Color c = goffScreen.getColor();
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        goffScreen.setColor(c);
        paint(goffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("bullet count: " + bullets.size(), 10, 60);
        g.drawString("enemy tanks count: " + tanks.size(), 10, 80);
        g.drawString("explosion count: " + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);
        // use b.paint(g) will have the concurrent issue. since use the iterator will have the concurrent issue!!!!
//        for (int i = 0; i < tanks.size(); i++) {
//            tanks.get(i).paint(g);
//        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        // java 8 stream api
        tanks.values().stream().forEach(e -> e.paint(g));

        for (int i = 0; i < explodes.size(); i++) {
            if (explodes.size() > 0)
                explodes.get(i).paint(g);
        }
//        for(Iterator<Bullet> it = bullets.iterator(); it.hasNext();){
//            Bullet b = it.next();
//            if(!b.isLive()){
//                it.remove();
//            }
//        }
        //TODO: here is the problem
        Collection<Tank> values = tanks.values();
        for (int i = 0; i < bullets.size(); i++) {
//            for (int j = 0; j < tanks.size(); j++) {
//                bullets.get(i).collideWith(tanks.get(j));
//            }
            for (Tank t : values) {
                bullets.get(i).collideWith(t);
            }
        }
    }

    public void addTank(Tank t) {
        tanks.put(t.getId(), t);
    }

    public Tank findTankByUUID(UUID id) {
        return tanks.get(id);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public Bullet findBulletByUUID(UUID id) {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getId().equals(id)) {
                return bullets.get(i);
            }
        }
        return null;
    }

    class MyKeyListener extends KeyAdapter {
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    setMainTankDir();
                    break;
                default:
                    break;
            }
            //setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    setMainTankDir();
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            //setMainTankDir();
        }

        private void setMainTankDir() {
            DIR dir = myTank.getDir();
            if (!bL && !bU && !bR && !bD) {
                myTank.setMoving(false);
                Client.INSTANCE.send(new TankStopMsg(getMainTank()));
            } else {
                if (bL) myTank.setDir(DIR.LEFT);
                if (bR) myTank.setDir(DIR.RIGHT);
                if (bU) myTank.setDir(DIR.DOWN);
                if (bD) myTank.setDir(DIR.UP);
                // send the tank moving message
                if (!myTank.isMoving())
                    Client.INSTANCE.send(new TankStartMovingMsg(getMainTank()));

                myTank.setMoving(true);

                if (dir != myTank.getDir()) {
                    Client.INSTANCE.send(new TankDirChangeMsg(getMainTank()));
                }
            }

        }
    }
}
