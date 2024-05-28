package com.msb.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

    // Facade pattern
    GameModel gm = new GameModel();
    static final int GAME_WIDTH = PropertyMgr.getIntValue(Constants.GAME_WIDTH);
    static final int GAME_HEIGHT = PropertyMgr.getIntValue(Constants.GAME_HEIGHT);
    //private int myTankSpeed = PropertyMgr.getIntValue(Constants.TANK_SPEED);
//    Tank myTank = new Tank(200, 400, DIR.UP, Group.GOOD, gm);
//    ArrayList<Tank> tanks = new ArrayList<>();
//    ArrayList<Explode> explodes = new ArrayList<>();
//    public ArrayList<Bullet> bullets = new ArrayList<>();

    @Override
    public void paint(Graphics g) {
        gm.paint(g);
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
                    gm.getMainTank().fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            Tank myTank = gm.getMainTank();
            if (!bL && !bU && !bR && !bD) {
                myTank.setMoving(false);
                return;
            }
            gm.getMainTank().setMoving(true);
            if (bL) myTank.setDir(DIR.LEFT);
            if (bR) myTank.setDir(DIR.RIGHT);
            if (bU) myTank.setDir(DIR.DOWN);
            if (bD) myTank.setDir(DIR.UP);
        }
    }
}
