package com.msb.tank.observer.v9;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        Button b = new Button("mybutton");
        b.addActionListener(new MyActionListener());
        b.addActionListener(new MyActionListener1());
        b.buttonPressed();
    }
}

class Button {
    private ArrayList<ActionListener> actionListeners = new ArrayList<>();
    private String name;

    public String getName() {
        return name;
    }

    public Button(String name) {
        this.name = name;
    }

    public void buttonPressed() {
        ActionEvent e = new ActionEvent(System.currentTimeMillis(), this);
        for (ActionListener l : actionListeners) {
            l.actionPerformed(e);
        }
    }

    public void addActionListener(ActionListener l) {
        actionListeners.add(l);
    }
}

// like Observer
interface ActionListener {
    void actionPerformed(ActionEvent e);
}

class MyActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(((Button)e.getSource()).getName());
    }
}

class MyActionListener1 implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("button pressed1");
    }
}
