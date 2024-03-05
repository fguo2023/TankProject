package com.msb;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class TankFrame extends Frame {

    static final int GAME_WIDTH = 800;
    static final int GAME_HEIGHT = 600;

    ArrayList<Bullet> bullets = new ArrayList<>();
    Tank myTank = new Tank(200, 200, DIR.DOWN, this);
    //Bullet b = new Bullet(300, 300, DIR.UP);

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
        g.setColor(c);
        myTank.paint(g);
        // use b.paint(g) will have the concurrent issue. since use the iterator will have the concurrent issue!!!!
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
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
                case KeyEvent.VK_UP:
                    bD = true;
                    break;
                case KeyEvent.VK_DOWN:
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
                    bD = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bU = false;
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
            if (bU) myTank.setDir(DIR.UP);
            if (bD) myTank.setDir(DIR.DOWN);
        }
    }
}
