package package1;

import org.junit.*;

import java.util.*;

/**
 * @author mkyong
 *
 */
public class Classe1Test {

    @Test
    public void testJ() {
        Classe1 c = new Classe1();
        Assert.assertEquals(1,c.getJ());
    }

    @Test
    public void testK() {
        Classe1 c = new Classe1();
        Assert.assertEquals(1,c.getK());
    }

    @Test
    public void testDouble() {
        Classe1 c = new Classe1();
        Assert.assertEquals(2.5,c.getDoubleVar(), 0.01);
    }
}
