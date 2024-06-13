package com.msb.nettystudy.netty.s08;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {
    public static final ServerFrame INSTANCE = new ServerFrame();

    Button btnStart = new Button("start");
    TextArea taLeft = new TextArea();
    TextArea taRight = new TextArea();

    Server server = new Server();

    public ServerFrame() throws HeadlessException {
        this.setSize(1600,600);
        this.setLocation(300,30);
        this.add(btnStart, BorderLayout.NORTH);
        Panel p = new Panel(new GridLayout(1,2));
        p.add(taLeft);
        p.add(taRight);
        this.add(p);

        taLeft.setFont(new Font("verderna", Font.PLAIN, 25));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();
    }

    public void updateServerMessage(String s) {
         this.taLeft.setText(taLeft.getText() + System.getProperty("line.separator") + s);
    }
    public void updateClientMessage(String s) {
        this.taRight.setText(taRight.getText() + System.getProperty("line.separator") + s);
    }
}

