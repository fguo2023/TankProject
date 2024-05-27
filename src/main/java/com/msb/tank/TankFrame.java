package com.msb.tank;

import com.msb.tank.abstracttankfac.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class TankFrame extends Frame {

    public static final int GAME_WIDTH = PropertyMgr.getIntValue(Constants.GAME_WIDTH);
    public static final int GAME_HEIGHT = PropertyMgr.getIntValue(Constants.GAME_HEIGHT);

    public ArrayList<BaseBullet> bullets = new ArrayList<>();

    private int myTankSpeed = PropertyMgr.getIntValue(Constants.TANK_SPEED);
    //BaseTank myTank = this.gameFactory.createTank(200, 400, DIR.UP, myTankSpeed, Group.GOOD, this);
    Tank myTank =  new Tank(200, 400, DIR.UP, myTankSpeed, Group.GOOD, this);
    public ArrayList<BaseTank> tanks = new ArrayList<>();
    public ArrayList<BaseExplode> explodes = new ArrayList<>();

    // init the Factory
    //new RectFactory(); // could be created in the config file.
    public GameFactory gameFactory;

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
        try {
            gameFactory = (GameFactory) Class.forName((String) PropertyMgr.get(Constants.FACTORY_NAME)).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            if (!bL && !bU && !bR && !bD) {
                myTank.setMoving(false);
                return;
            }
            myTank.setMoving(true);
            if (bL) myTank.setDir(DIR.LEFT);
            if (bR) myTank.setDir(DIR.RIGHT);
            if (bU) myTank.setDir(DIR.DOWN);
            if (bD) myTank.setDir(DIR.UP);
        }
    }
}
