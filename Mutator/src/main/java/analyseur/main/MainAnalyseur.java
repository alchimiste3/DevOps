package analyseur.main;

import analyseur.analyse.Analyseur;

/**
 * 
 * @author Quentin Laborde
 *
 */
public class MainAnalyseur {


    
    public static void main(String[] args) {
        
        String dossierTestXML = "surefire-reports/";
        String dossierHTML = "surefire-reports/";
        String nomFichierHtml = "result.html";
        String nomSerieTest = "nom de test par défaut";
        String nomFichierMutantXML = "listeMutant.xml";
        
        if(args.length >= 3){
            dossierTestXML = args[0];
            dossierHTML = args[1];
            nomFichierHtml = args[2];
            nomSerieTest = args[3];
        }
        
        Analyseur analyseur = new Analyseur(dossierTestXML,dossierHTML, nomFichierHtml,nomSerieTest,nomFichierMutantXML);
        analyseur.AnalyserFichiersTests();
    }
}
