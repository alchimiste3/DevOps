package analyseur.main;

import java.util.ArrayList;

import analyseur.analyse.Mutant;
import analyseur.lecture_ecriture.EcrireXML;
import analyseur.lecture_ecriture.GenererHTML;
import analyseur.lecture_ecriture.LireXML;

public class MainFinal {
    
    public static void main(String[] args) {
        String dossierHTML = "surefire-reports/";
        String nomFichierHtml = "result.html";
        String nomFichierMutantXML = "listeMutant.xml";
        String nomFichierConfXML = "../conf.xml";
        
        if(args.length >= 3){
            dossierHTML = args[0];
            nomFichierHtml = args[1];
            nomFichierMutantXML = args[2];
            nomFichierConfXML = args[3];
            
        }
        


        LireXML lire = new LireXML();
        GenererHTML gen = new GenererHTML(dossierHTML, nomFichierHtml);
        
        
        ArrayList<Mutant> liste = lire.lireMutant(nomFichierMutantXML,nomFichierConfXML);
        gen.genererTableauxMutantMort(liste);
        gen.genererTableauxMutantVivant(liste);
        gen.genererTableauxListeTests(liste);
        gen.ecrireFin();


    }
    
}
