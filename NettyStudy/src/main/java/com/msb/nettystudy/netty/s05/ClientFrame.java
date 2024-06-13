package com.msb.nettystudy.netty.s05;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {
    // 做一个聊天过程
    TextArea ta = new TextArea();
    TextField tf = new TextField();
    Client client = null;

    public ClientFrame() throws HeadlessException {
        this.setSize(600, 400);
        this.setLocation(100, 20);
        this.setVisible(true);
        this.add(ta, BorderLayout.CENTER);
        this.add(tf, BorderLayout.SOUTH);
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.send(tf.getText());
                ta.setText(ta.getText() + tf.getText());
                tf.setText("");
            }
        });
        addWindowListener(new WindowAdapter() { // the X on the window
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        connectToServer(); // after the window appears, call client to connect to the netty server
    }

    private void connectToServer() {
        client = new Client();
        client.connect();
    }

    public static void main(String[] args) {
        new ClientFrame();
    }
}
