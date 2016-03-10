package analyseur.main;

import analyseur.analyse.Analyseur;

/**
 * Created by sualty on 10/03/16.
 */
public class MainMortsNes {

    public static void main(String[] args) {

        String dossierTestXML = "surefire-reports/";
        String dossierHTML = "surefire-reports/";
        String nomFichierHtml = "result.html";
        String nomSerieTest = "nom de test par défaut";
        String nomFichierMutantXML = "listeMutant.xml";

        System.out.println("args.length = "+args.length);
        if(args.length >= 4){
            dossierTestXML = args[0];
            dossierHTML = args[1];
            nomFichierHtml = args[2];
            nomSerieTest = args[3];
        }

        Analyseur analyseur = new Analyseur(dossierTestXML,dossierHTML, nomFichierHtml,nomSerieTest,nomFichierMutantXML);
        analyseur.indiquerMortNe();
    }
}