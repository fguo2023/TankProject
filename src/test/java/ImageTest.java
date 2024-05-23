import com.msb.DIR;
import com.sun.java.swing.plaf.windows.WindowsTabbedPaneUI;
import org.junit.Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static org.junit.Assert.*;

public class ImageTest {

    @Test
    public void test(){
        //fail("Not yet implemented");
        try {
            //BufferedImage image = ImageIO.read(new File("C:/TankTank/TankProject/src/images/Screenshot 2023-06-04 205147.png"));
            //assertNotNull(image);
            //C:\TankTank\TankProject\src\images\bulletD.gif
            InputStream resourceAsStream = ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif");
            BufferedImage image2 = ImageIO.read(resourceAsStream);
            assertNotNull(image2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDir(){
        Random random = new Random();
        System.out.println(DIR.values()[random.nextInt(4)]);
        System.out.println(DIR.values()[0]);
        System.out.println(DIR.values()[1]);
        System.out.println(DIR.values()[2]);
        System.out.println(DIR.values()[3]);
    }


}
