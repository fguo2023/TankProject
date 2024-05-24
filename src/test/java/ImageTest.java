import com.msb.tank.DIR;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.plaf.ComponentInputMapUIResource;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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

    @Test
    public void testComparator(){
        List<Integer> arr = Arrays.asList(2,3,4,1);
        arr.sort(new Comparator<Integer>() { // strategy pattern using Comparator
            @Override
            public int compare(Integer o1, Integer o2) {
                if(o1.equals(o2)) return 0;
                return o1 < o2 ? -1 : 1;
            }
        });
        System.out.println(arr);
    }
    @Test
    public void testClassReflection() throws Exception {
        Class<?> clazz = Class.forName("com.msb.tank.strategy.FireOneBullet");
        if(clazz != null){
            clazz.getDeclaredConstructor().newInstance();
        }
        assertNotNull(clazz);
    }
}
