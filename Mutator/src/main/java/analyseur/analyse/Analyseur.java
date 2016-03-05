package analyseur.analyse;

import java.io.*;
import java.util.ArrayList;

import analyseur.lecture_ecriture.EcrireXML;
import analyseur.lecture_ecriture.GenererHTML;

import analyseur.lecture_ecriture.LireXML;


public class Analyseur {
    
    private String repetoireTest;
    private String repertoireHTML;
    private String nomFichierHtml;
    private String nomSerieTest;
    private String nomFichierMutantXML;
    private ArrayList<String> listeFichierTest;
    

    public Analyseur(String repetoireTest, String repertoireHTML, String nomFichierHtml,String nomSerieTest, String nomFichierMutantXML){
        this.repetoireTest = repetoireTest;
        this.repertoireHTML = repertoireHTML;
        this.nomFichierHtml = nomFichierHtml;
        this.nomSerieTest = nomSerieTest;
        this.nomFichierMutantXML = nomFichierMutantXML;
        listeFichierTest = new ArrayList<String>();
    }
    

    public void AnalyserFichiersTests(){
        
        Mutant mutant = new Mutant(nomSerieTest);
        
        ArrayList<ArrayList<Test>> list = new ArrayList();
        
        ListerFichierTest();
        
        GenererHTML genHTML = new GenererHTML(repertoireHTML,nomFichierHtml);
        
        genHTML.ecrireDebutTableauTestParClass(nomSerieTest);
        
        for(String t : listeFichierTest){

            LireXML parser = new LireXML();
            
            TestsParClass testClass = parser.lireTests(repetoireTest+t);
            list.add(testClass.getListeTests());
            mutant.addTestParClass(testClass);
            genHTML.genererTableauxTestParClass(testClass, t);

        }
        
        EcrireXML ecrire = new EcrireXML();
        ecrire.ecrireMutant(mutant, nomFichierMutantXML);
        genHTML.ecrireFinTableauTestParClass(list);
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
