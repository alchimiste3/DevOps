package package1;

public class Classe1 {

    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private double doubleVar;
    public Classe1() {
        j=0;k=0;l=0;m=0;n=0;
        incJ(2);incK();incL();incN();
        m=m+1;
        double a = 5;
        double b = 2;
        double c = a / b;
        doubleVar = c;
    }

    public void incJ(int g) {
        this.j=j+1;
    }

    public void incK() {
        this.k=k+1;
    }

    public void incL() {
        this.l=l+1;
    }

    public void incM() {
        this.m=m+1;
    }

    public void incN() {
        this.n=n+1;
    }

    public int getJ() {
        return j;
    }
    public int getK() {
        return k;
    }
    public int getL() {
        return l;
    }
    public int getM() {
        return m;
    }
    public int getN() {
        return n;
    }
    public double getDoubleVar() {
        return doubleVar;
    }
}
