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

    public GenererHTML() {
        f = new File("surefire-reports/result.html");
        try {
            bw = new BufferedWriter(new FileWriter(f));
        }
        catch(IOException e) {
            System.out.println("Impossible de creer le fichier de resultats" + e.getStackTrace());
        }
    }

    public void genererListeTests(ArrayList<Test> listeTests, String nomFichier) {
        try {
            
            int nbFails = 0;
            int nbTests = 0;
            
            for (Test test : listeTests){
                System.out.println("r" + test.toString());
                if (test.isFail() == false)
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
            
            if(listeTests.get(0).isFail())bw.write("<td class=\"fail\" >echec</td><td class=\"fail\">"+listeTests.get(0).isTypeFail()+"</td>");
            else bw.write("<td class=\"nofail\">reussie</td><td class=\"nofail\"></td>");
            
            
            bw.write("</tr>");
            
            //On parcourt les autres test
            for (int i = 1; i< listeTests.size() ; i++){
                
                Test test = listeTests.get(i);
                bw.write("<tr>");
                bw.write("<td>"+test.getNom()+"</td>");
                
                if(!test.isFail())bw.write("<td class=\"fail\" >echec</td><td class=\"fail\">"+test.isTypeFail()+"</td>");
                else bw.write("<td class=\"nofail\">reussie</td><td class=\"nofail\"></td>");
            
                
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
            bw.write("</tr>");
        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }
    }
    
    public void ecrireFinTableau(){
        try {
            bw.write("</tabler>");
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