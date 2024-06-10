package com.msb.nettystudy.io.bio;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        // the difficult part to write the network program is exception handling and 关闭-> 线程的正常结束 或者 线程池的正常结束
        // this bio, 半双工
        Socket s = new Socket("127.0.0.1", 8888); // connect to the server
        s.getOutputStream().write("Hello Server".getBytes());
        s.getOutputStream().flush();

        System.out.println("write over, waiting for msg back...");
        byte[] bytes = new byte[1024];
        int len = s.getInputStream().read(bytes);
        System.out.println(new String(bytes, 0, len));
        s.close();
    }
}
