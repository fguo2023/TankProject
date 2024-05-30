package com.msb.tank.observer.v9;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        Button b = new Button();
        b.addActionListener(new MyActionListener());
        b.addActionListener(new MyActionListener1());
        b.buttonPressed();
    }
}

class Button {
    private ArrayList<ActionListener> actionListenerList = new ArrayList<>();

    public void buttonPressed() {
        ActionEvent e = new ActionEvent(System.currentTimeMillis(), this);
        for (ActionListener l : actionListenerList) {
            l.actionPerformed(e);
        }
    }

    public void addActionListener(ActionListener l) {
        actionListenerList.add(l);
    }
}

interface ActionListener {
    public void actionPerformed(ActionEvent e);
}

class MyActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("button pressed");
    }
}

class MyActionListener1 implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("button pressed1");
    }
}
