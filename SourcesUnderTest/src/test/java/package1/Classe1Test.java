package package1;

import org.junit.*;

import java.util.*;

/**
 * @author mkyong
 *
 */
public class Classe1Test {

    @Test
    public void testClasse1() {
        Classe1 c = new Classe1();
        int k = 1;
        Assert.assertEquals(k,c.getI());
        System.out.println("@Test - testEmptyCollection");
    }
}
