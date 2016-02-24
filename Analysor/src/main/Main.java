package main;

import analyse.Analyseur;

public class Main {

    public static void main(String[] args) {
        Analyseur analyseur = new Analyseur();
        analyseur.AnalyserFichiersTests("TEST-package1.Classe1Test.xml");
    }
}
