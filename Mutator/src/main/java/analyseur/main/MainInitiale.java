package analyseur.main;

import analyseur.lecture_ecriture.EcrireXML;
import analyseur.lecture_ecriture.GenererHTML;
import analyseur.lecture_ecriture.LireXML;

/**
 * 
 * @author Quentin Laborde
 *
 */
public class MainInitiale {
    

    public static void main(String[] args) {
        
       String dossierHTML = "surefire-reports/";
       String nomFichierHtml = "result.html";
       String nomFichierMutantXML = "listeMutant.xml";
       
       String nomFichierConfXML = "../conf.xml";
       String nomFichierProcTxt = "../processors.txt";
       
       if(args.length >= 5){
           dossierHTML = args[0];
           nomFichierHtml = args[1];
           nomFichierMutantXML = args[2];
           nomFichierConfXML = args[3];
           nomFichierProcTxt = args[4];

       }
       
       
       EcrireXML e = new EcrireXML();
       e.viderXMLMutants(nomFichierMutantXML);
       
       LireXML l = new LireXML();
       l.lireProcessors(nomFichierConfXML, nomFichierProcTxt); 

       
       GenererHTML gen = new GenererHTML(dossierHTML, nomFichierHtml);    
       gen.ecrireDebut();

   }   
   
}
