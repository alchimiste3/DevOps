package package2;

import package1.Classe1;

/**
 * Created by sualty on 24/02/16.
 */
public class Classe2 {
    private int i;
    public Classe2() {
        Classe1 c = new Classe1();
        c.incN();
        i=0;
        incI();
    }
    public void incI() {
        i=i+1;
    }
    public int getI() {
        return i;
    }
}
