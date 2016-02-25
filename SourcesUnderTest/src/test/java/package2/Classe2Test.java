package package2;

import org.junit.*;

import java.util.*;

/**
 * @author mkyong
 *
 */
public class Classe2Test {

    @Test
    public void testJ() {
        Classe2 c = new Classe2();
        Assert.assertEquals(1,c.getI());
    }
}
