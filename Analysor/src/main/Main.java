package main;

import analyse.Analyseur;

public class Main {


    
    public static void main(String[] args) {
        
        String dossierTestXML = "surefire-reports/";
        String dossierHTML = "surefire-reports/";
        String nomFichierHtml = "result.html";

        
        if(args.length >= 2){
            dossierTestXML = args[0];
            dossierHTML = args[1];
            //if(args[2] != null) nomFichierHtml = args[2];

        }
        
        Analyseur analyseur = new Analyseur(dossierTestXML,dossierHTML, nomFichierHtml);
        analyseur.AnalyserFichiersTests();
    }
}
