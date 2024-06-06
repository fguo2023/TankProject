package com.msb.tank.adapter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        // adapter pattern, JDBC -> ODBC bridge is the adapter pattern
        FileInputStream fis = new FileInputStream("c:/test.text");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr); // bufferReader can only pass reader, not the string, but inputStreamReader can read the file as the stream
        String line = br.readLine();
        while(line != null && !line.equals("")){
            System.out.println(line);
        }
        br.close();
        /*
        /------------------------------------------------------------------------------*/
        // WindowAdapter is not the adapter pattern
        // why you need the WindowAdapter, not the WindowListener, 因为如果你只关心WindowListener的其中一个方法，你不需要全部重写一遍， 只要重写你关心的那个方法like windowClosing
        Frame f = new Frame();
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
    }
}
