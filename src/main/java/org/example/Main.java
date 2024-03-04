package org.example;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
       Frame f = new Frame();
       f.setSize(800,600);
       f.setResizable(false);
       f.setTitle("tank war");
       f.setVisible(true);
       f.addWindowListener(new WindowAdapter() { // the X on the window
           @Override
           public void windowClosing(WindowEvent e) {
               System.exit(0);
           }
       });
    }
}