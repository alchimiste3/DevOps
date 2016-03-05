package analyseur.main;

import analyseur.lecture_ecriture.EcrireXML;
import analyseur.lecture_ecriture.GenererHTML;

public class MainInitiale {
    

    public static void main(String[] args) {
        
       String dossierHTML = "surefire-reports/";
       String nomFichierHtml = "result.html";
       String nomFichierMutantXML = "listeMutant.xml";
       
       if(args.length >= 3){
           dossierHTML = args[0];
           nomFichierHtml = args[1];
           nomFichierMutantXML = args[2];

       }
       
       
       EcrireXML e = new EcrireXML();
       e.viderXMLMutants(nomFichierMutantXML);
       
       GenererHTML gen = new GenererHTML(dossierHTML, nomFichierHtml);    
       gen.ecrireDebut();

   }   
   
}
