package main;

import analyse.Analyseur;

public class Main {

    public static void main(String[] args) {
        Analyseur analyseur = new Analyseur("surefire-reports/");
        analyseur.AnalyserFichiersTests();
    }
}
