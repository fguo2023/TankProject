package com.msb.tank.observer.v8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener implements ActionListener {
    public MyActionListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("button pressed");
    }
}
