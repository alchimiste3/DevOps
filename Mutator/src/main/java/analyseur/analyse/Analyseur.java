package analyseur.analyse;

import java.io.*;
import java.util.ArrayList;

import analyseur.lecture_ecriture.GenererHTML;

import analyseur.lecture_ecriture.LireXML;


public class Analyseur {
    
    private String repetoireTest;
    private String repertoireHTML;
    private String nomFichierHtml;
    
    private ArrayList<String> listeFichierTest;
    

    public Analyseur(String repetoireTest, String repertoireHTML, String nomFichierHtml){
        this.repetoireTest = repetoireTest;
        this.repertoireHTML = repertoireHTML;
        this.nomFichierHtml = nomFichierHtml;
        listeFichierTest = new ArrayList<String>();
    }
    

    public void AnalyserFichiersTests(){
        ArrayList<ArrayList<Test>> list = new ArrayList();
        
        ListerFichierTest();
        GenererHTML genHTML = new GenererHTML(repertoireHTML,nomFichierHtml);
        
        genHTML.ecrireDebut();
        genHTML.ecrireDebutTableau();
        
        for(String t : listeFichierTest){
            
            System.out.println("\n"+t);
            LireXML parser = new LireXML();
            
            TestsParClass testClass = parser.lireTests(repetoireTest+t);
            genHTML.genererTableauxTests(testClass, t);

        }
        
        genHTML.ecrireFinTableau(list);
        genHTML.ecrireFin();
    }
    
    /**
     * Permet de trouver les fichier xml dans le dossier qui contient les résultat des tests
     */
    private void ListerFichierTest(){
        File repertoire = new File(repetoireTest);  
        String list[] = repertoire.list();
        
        for(String f : list){
            if(f.endsWith(".xml")==true){
                listeFichierTest.add(f);
            }
            
        }

    }
    
}
