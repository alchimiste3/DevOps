package package1;

public class Classe1 {

    private int j;
    private int k;
    private double doubleVar;
    public Classe1() {
        j=0;k=0;
        j=j+1;
        if(k<=j)
            k++;

        double a = 5;
        double b = 2;
        double c = a / b;
        doubleVar = c;
    }


    public int getJ() {
        return j;
    }
    public int getK() {
        return k;
    }

    public double getDoubleVar() {
        return doubleVar;
    }
}
