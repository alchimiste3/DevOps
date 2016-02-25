/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecture_ecriture;

import analyse.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class GenererHTML {
    private File f;
    private BufferedWriter bw;

    public GenererHTML(String repertoireHTML, String nomFichierHtml) {
        f = new File(repertoireHTML + nomFichierHtml);
        
        try {
            bw = new BufferedWriter(new FileWriter(f));
        }
        catch(IOException e) {
            System.out.println("Le dossier pour ajouter le html n'a pas était trouvé" + e.getStackTrace());
        }
    }

    public void genererListeTests(ArrayList<Test> listeTests, String nomFichier) {
        try {
            
            int nbFails = 0;
            int nbTests = 0;
            
            for (Test test : listeTests){
                System.out.println("r" + test.toString());
                if (test.isFail() != false)
                    nbFails++;
                nbTests++;
            }
            
            bw.write("<h2>Resultats du fichier : " + nomFichier + "</h2>");
            bw.write("<p>");
            bw.write("<h3> Nombre de mutants testes : " + nbTests + "</h3>");
            bw.write("<h3> Nombre de mutants tues : " + nbFails + "</h3>");
            bw.write("<ul>");
            
            for (Test test : listeTests) {
                bw.write("<li>" + test.isFail() + " : " + test.isTypeFail() + "  " + test.getNomClass() + " : " + test.getNom() + "</li>");
            }
            
            bw.write("</ul>");
            bw.write("</p>");
            
        } 
        catch(IOException e){
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }
    }
    
    public int pourcentageFail(ArrayList<ArrayList<Test>> listeTests){
        int compteurTestFail = 0;
        int compteurTest = 0;
        
        for (ArrayList<Test> l : listeTests){
            for (Test test : l){
                compteurTest++;
                if (test.isFail() != false){
                    compteurTestFail++;
                }
            }
            
        }
        
        return (compteurTestFail*100)/compteurTest;
    }
        
    public void genererTableauxTests(ArrayList<Test> listeTests, String nomFichier) {
        try {
            
            int nbFails = 0;
            int nbTests = 0;
            
            for (Test test : listeTests){
                System.out.println("r" + test.toString());
                if (test.isFail() == false)
                    nbFails++;
                nbTests++;
            }
            
            //On parcourt le premier test en ajoutant le nom de la class
            
            bw.write("<tr>");
            bw.write("<td rowspan="+ listeTests.size() +">"+ listeTests.get(0).getNomClass()+"</td>");
            bw.write("<td>"+listeTests.get(0).getNom()+"</td>");
            
            if(listeTests.get(0).isFail())
                bw.write("<td class=\"fail\" >echec</td><td class=\"fail\">"+listeTests.get(0).isTypeFail()+"</td>");
            else
                bw.write("<td class=\"nofail\">reussie</td><td class=\"nofail\"></td>");
            
            
            bw.write("</tr>");
            
            //On parcourt les autres test
            for (int i = 1; i< listeTests.size() ; i++){
                
                Test test = listeTests.get(i);
                bw.write("<tr>");
                bw.write("<td>"+test.getNom()+"</td>");
                
                if(test.isFail())
                    bw.write("<td class=\"fail\" >echec</td><td class=\"fail\">"+test.isTypeFail()+"</td>");
                else
                    bw.write("<td class=\"nofail\">reussie</td><td class=\"nofail\"></td>");
            
                
                bw.write("</tr>");
            }
            

        } 
        catch(IOException e){
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }

        
    }
    
    public void ecrireDebutTableau(){
        try {
            bw.write("<table border>");
            bw.write("<tr>");
            bw.write("<td>Class Test</td> <td>Test</td> <td>Resultat</td> <td>Type Erreur</td>");
            bw.write("<h2>Résultats des tests</h2");
            bw.write("</tr>");
        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }
    }
    
    public void ecrireFinTableau(ArrayList<ArrayList<Test>> listeTests){
        try {
            bw.write("</tabler>");
            bw.write("<h4>Pourcentage d'échecs = "+ pourcentageFail(listeTests) +"%</h4");

        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());

        }
    }
    
    public void ecrireDebut() {
        try {
            bw.write("<html>");
            bw.write("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Resultats</title><link href=\"result.css\" rel=\"stylesheet\" media=\"all\" type=\"text/css\"></head>");
            bw.write("<body>");
        } catch(IOException e) {
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }
        
    }
    
    public void ecrireFin() {
        try {
            bw.write("</body>");
            bw.append("</html>");
            bw.close();
        } catch(IOException e) {
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }
        
    }
}
