package main;

import analyse.Analyseur;

public class Main {


    
    public static void main(String[] args) {
        
        String dossierTestXML = "surefire-reports/";
        String dossierHTML = "surefire-reports/";
        
        if(args.length >= 2){
            dossierTestXML = args[0];
            dossierHTML = args[1];
        }
        
        Analyseur analyseur = new Analyseur(dossierTestXML,dossierHTML);
        analyseur.AnalyserFichiersTests();
    }
}
