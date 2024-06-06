package com.msb.tank.ASM;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ClassTransformerTest {
    public static void main(String[] args) throws IOException {
        //ClassReader cr = new ClassReader(ClassPrinter.class.getClassLoader().getResourceAsStream("com.msb.tank.ASM.Tank.class"));
/*

        ClassWriter cw = new ClassWriter(0);
        //cr.accept(cw,0);
        byte[] b2 = cw.toByteArray();

        String path = (String) System.getProperties().get("user.dir");
        File f = new File(path + "/com/msb/dp/ASM/");
        f.mkdir();

        FileOutputStream fos = new FileOutputStream(new File(path + "/com/msb/dp/ASM/Tank_0.class"));
        fos.write(b2);
        fos.flush();
        fos.close();
*/

    }
}
