package package1;

/**
 * Created by sualty on 24/02/16.
 */
public class Classe1 {

    private int i;
    private int k;
    public Classe1() {
        i=0;
        if(i<=0)
            i++;
        k=i+4;
        i=k-2;

    }

    public int getI() {
        return i;
    }
}
