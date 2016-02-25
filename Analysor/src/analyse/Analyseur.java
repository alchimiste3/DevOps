package analyse;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import lecture_ecriture.GenererHTML;

import lecture_ecriture.LireXML;


public class Analyseur {
    
    private String furefirePath;
    
    private ArrayList<String> listeFichierTest;
    

    public Analyseur(String repetoireTest){
        furefirePath = repetoireTest;
        listeFichierTest = new ArrayList<>();
    }
    

    public void AnalyserFichiersTests(){
        ListerFichierTest();
        GenererHTML genHTML = new GenererHTML();
        
        genHTML.ecrireDebut();
        genHTML.ecrireDebutTableau();
        
        for(String t : listeFichierTest){
            System.out.println("\n"+t);
            LireXML parser = new LireXML();
            ArrayList<Test> listTest = parser.lireTest(furefirePath+t);
                        
            genHTML.genererTableauxTests(listTest, t);

            for(Test test : listTest){
                System.out.println("\n"+test.display());
            }
        }
        
        genHTML.ecrireFinTableau();
        genHTML.ecrireFin();
    }
    
    /**
     * Permet de trouver les fichier xml dans le dossier qui contient les résultat des tests
     */
    private void ListerFichierTest(){
        File repertoire = new File(furefirePath);  
        String list[] = repertoire.list();
        
        for(String f : list){
            if(f.endsWith(".xml")==true){
                listeFichierTest.add(f);
            }
            
        }

    }
    
}
