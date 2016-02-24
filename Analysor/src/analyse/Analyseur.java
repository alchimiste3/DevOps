package analyse;

import java.io.*;
import java.util.ArrayList;

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
        
        for(String t : listeFichierTest){
            System.out.println("\n"+t);
            LireXML parser = new LireXML();
            ArrayList<Test> listTest = parser.lireTest(furefirePath+t);
            
            for(Test test : listTest){
                System.out.println("\n"+test.display());
            }
        }

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
