package analyseur.main;

import java.util.ArrayList;

import analyseur.analyse.Mutant;
import analyseur.lecture_ecriture.EcrireXML;
import analyseur.lecture_ecriture.GenererHTML;
import analyseur.lecture_ecriture.LireXML;

/**
 * 
 * @author Quentin Laborde
 *
 */
public class MainFinal {
    
    /**
     * Methode qui lance le traitement final des resultats de test 
     * @param args
     */
    public static void main(String[] args) {
        
        //Chemin vers les dossier et fichier a modifier et lire
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

        int nb_mn = lire.getTotalMortsNes(nomFichierMutantXML);
        ArrayList<Mutant> liste = lire.lireMutant(nomFichierMutantXML,nomFichierConfXML);
        for(Mutant mutant : liste) {
            System.out.println("bloublou "+mutant.getNom());
        }
        gen.totalMortsNes(nb_mn);
        gen.genererTableauxMutantMort(liste);
        gen.genererTableauxMutantVivant(liste);
        gen.PourcentageMutantMortVivant(liste);

        gen.ecrireFin();


    }
    
}
