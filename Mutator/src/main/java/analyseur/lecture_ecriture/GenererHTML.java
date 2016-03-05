/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyseur.lecture_ecriture;

import analyseur.analyse.Mutant;
import analyseur.analyse.Test;
import analyseur.analyse.TestsParClass;

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
    private FileWriter fileWriter;

    public GenererHTML(String repertoireHTML, String nomFichierHtml) {
        f = new File(repertoireHTML + nomFichierHtml);
        
        try {
            fileWriter = new FileWriter(f.getAbsolutePath(),true);
            bw = new BufferedWriter(fileWriter);

        }
        catch(IOException e) {
            System.out.println("Le dossier pour ajouter le html n'a pas était trouvé" + e.getStackTrace());
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
        
        if(compteurTest == 0){
            return 0;
        }
        return (compteurTestFail*100)/compteurTest;
    }
        
///////////////////////////////// tableau pour chaque class test //////////////////////////////////
    
    public void genererTableauxTestParClass(TestsParClass testClass, String nomFichier) {
        try {
            
            int nbFails = 0;
            int nbTests = 0;
            
            ArrayList<Test> listeTests = testClass.getListeTests();
            
            for (Test test : listeTests){
                if (test.isFail() == false)
                    nbFails++;
                nbTests++;
            }
            
                       
            
            bw.write("<tr>");
            bw.write("<td rowspan="+ testClass.getNombreTest() +">"+ testClass.getNomClass() +"</td>");
            bw.write("<td rowspan="+ testClass.getNombreTest() +">"+ testClass.getNombreTest()+"</td>");
            bw.write("<td rowspan="+ testClass.getNombreTest() +">"+ testClass.getTempsExecution()+" s</td>");
            bw.write("<td rowspan="+ testClass.getNombreTest() +">"+ testClass.getNombreTestSkipped()+"</td>");

            bw.write("<td>"+listeTests.get(0).getNom()+"</td>");
            
            if(listeTests.get(0).isFail())
                bw.write("<td class=\"fail\" >echec</td><td class=\"fail\">"+listeTests.get(0).getTypeFail()+"</td>");
            else
                bw.write("<td class=\"nofail\">reussie</td><td class=\"nofail\"></td>");
            
            
            bw.write("</tr>");
            
            
            //On parcourt les autres test
            for (int i = 1; i< listeTests.size() ; i++){
                
                Test test = listeTests.get(i);
                bw.write("<tr>");
                bw.write("<td>"+test.getNom()+"</td>");
                
                if(test.isFail())
                    bw.write("<td class=\"fail\" >echec</td><td class=\"fail\">"+test.getTypeFail()+"</td>");
                else
                    bw.write("<td class=\"nofail\">reussie</td><td class=\"nofail\"></td>");
            
                
                bw.write("</tr>");
            }
            

        } 
        catch(IOException e){
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }

        
    }
    
    public void ecrireDebutTableauTestParClass(String nomSerieTest){
        try {
            bw.write("<table border>");
            bw.write("<tr>");
            bw.write("<td>Class Test</td> <td>Nombre de tests</td> <td>Temps d'execution</td> <td>Tests ignorés</td> <td>Test</td> <td>Resultat</td> <td>Type Erreur</td>");
            bw.write("<h2>Résultats de la série de tests \""+nomSerieTest+"\"</h2>");
            bw.write("</tr>");
        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }
    }
    
    public void ecrireFinTableauTestParClass(ArrayList<ArrayList<Test>> listeTests){
        try {
            bw.write("</table>");
            bw.write("<h4>Pourcentage d'échec = "+ pourcentageFail(listeTests) +"%</h4>");
            bw.close();

        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());

        }
    }
    
    
///////////////////////////////// tableau final //////////////////////////////////

    public void genererTableauxListeTests(ArrayList<Mutant> listeMutant) {
        try {
            
            bw.write("</div><div><h2>résumé pour chaque tests : </h2>");
            bw.write("<table border>");
            bw.write("<tr>");
            bw.write("<td>Test</td> <td>Nombre d'execution réussie</td> <td>Nombre d'echec</td>");
            bw.write("</tr>");
            
            // liste de tout les tests 
            for(Mutant mut : listeMutant){
                ArrayList<Test> listeTest = mut.getListeTest();
                
                for (Test test : listeTest){
                    bw.write("<tr>");
                    bw.write("<td>"+test.getNom()+"</td>");
                    
                    if(test.isFail())
                        bw.write("<td class=\"fail\" >echec</td><td class=\"fail\">"+test.getTypeFail()+"</td>");
                    else
                        bw.write("<td class=\"nofail\">reussie</td><td class=\"nofail\"></td>");
                 
                    bw.write("</tr>");
                }
                
            }
            
            bw.write("</table>");
            bw.write("</div>");
            

        } 
        catch(IOException e){
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }

        
    }
    
    public void genererTableauxMutantMort(ArrayList<Mutant> listeMutant) {
        try {
            
            bw.write("</div><div><h2>Les mutant Mort</h2>");
            bw.write("<table border>");
            bw.write("<tr>");
            bw.write("<td>Mutant</td> <td>Nombre de tests réussie</td> <td>Nombre de test echoue</td>");
            bw.write("</tr>");
            
            // Liste des mutant tuer avec le nombre de test réussie et echoue
            for(Mutant mut : listeMutant){   
                if(!mut.getNombreTestFails().equals("0")){

                    bw.write("<tr>");
                    bw.write("<td>"+mut.getNom()+"</td>");
                    int nbTestNoFail = Integer.parseInt(mut.getNombreTest()) - Integer.parseInt(mut.getNombreTestFails());
    
                    bw.write("<td class=\"nofail\" >"+nbTestNoFail+"</td><td class=\"fail\">"+mut.getNombreTestFails()+"</td>");
                    bw.write("</tr>");
                }
                
            }
            
            bw.write("</table>");
            bw.write("</div>");

        } 
        catch(IOException e){
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }

        
    }
    
    public void genererTableauxMutantVivant(ArrayList<Mutant> listeMutant) {
        try {
            
            bw.write("</div><div><h2>Les mutant Vivant</h2>");
            bw.write("<table border>");
            bw.write("<tr>");
            bw.write("<td>Mutant</td> <td>Nombre de Tests ignorés</td>");
            bw.write("</tr>");
            
            // Liste des mutant tuer avec le nombre de test réussie et echoue
            for(Mutant mut : listeMutant){  
                if(mut.getNombreTestFails().equals("0")){
                    bw.write("<tr>");
                    bw.write("<td>"+mut.getNom()+"</td>");
                    bw.write("<td class=\"nofail\" >"+mut.getNombreTestSkipped()+"</td>");
                    bw.write("</tr>");
                }

                
            }
            
            bw.write("</table>");
            bw.write("</div>");

        } 
        catch(IOException e){
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }

        
    }
    
///////////////////////////////// Generer debut et fin html //////////////////////////////////

    public void ecrireDebut() {
        try {
            bw.write("<html>");
            bw.write("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Resultats</title><link href=\"result.css\" rel=\"stylesheet\" media=\"all\" type=\"text/css\"></head>");
            bw.write("<body>");
            bw.write("<div>");
            bw.close();

        } catch(IOException e) {
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }
        
    }
    
    public void ecrireFin() {
        try {
            System.out.println("\n\n\n\n\nezrvzseer\n\n\n\n\n\n");
            bw.write("</body>");
            bw.append("</html>");
            bw.close();
        } catch(IOException e) {
            System.out.println("9");

            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }
        
    }
}
