package analyseur.analyse;

import java.io.*;
import java.util.ArrayList;

import analyseur.lecture_ecriture.EcrireXML;
import analyseur.lecture_ecriture.GenererHTML;

import analyseur.lecture_ecriture.LireXML;

/**
 * Permet de creer un Analyseur pour parcourire les resultats des tests Junit d'un projet et ecrire l'analyse dans un html
 * @author user
 *
 */
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

    public void indiquerMortNe() {
        GenererHTML genHTML = new GenererHTML(repertoireHTML,nomFichierHtml);
        genHTML.ecrireMortNe(nomSerieTest);
        EcrireXML ecrire = new EcrireXML();
        ecrire.addMortNe(nomFichierMutantXML);
    }
    /**
     * Analyse chaque fichier xml creer lors de test Junit et remplie un tableau resumant chaque fichier
     */
    public void AnalyserFichiersTests(){
        
        //On creer un mutant
        Mutant mutant = new Mutant(nomSerieTest);
        
        ArrayList<ArrayList<Test>> list = new ArrayList<ArrayList<Test>>();
        
        //On liste les les nom des fichiers xml resultat
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
        
        //On ajoute le mutant au fichier xml listeMutant
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
