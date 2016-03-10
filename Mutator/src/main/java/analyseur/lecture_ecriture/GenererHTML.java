/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyseur.lecture_ecriture;

import analyseur.analyse.Mutant;
import analyseur.analyse.Mutation;
import analyseur.analyse.Test;
import analyseur.analyse.TestsParClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Permet de generer de remplire un fichier html
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

    public void ecrireMortNe(String nomSerieTest) {
        try {
            bw.write("<h2>La série de tests \""+nomSerieTest+"\" a échoué à la compilation</h2>");
            bw.close();
        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }
    }

    public void totalMortsNes(int nb_mn) {
        try {
            if(nb_mn==0) {
                bw.write("<h2> Tous les mutants sont passés à la compilation</h2>");
            }
            else if (nb_mn==1) {
                bw.write("<h2> Un mutant n'a pas compilé</h2>");
            }
            else{
                bw.write("<h2>"+ nb_mn +" mutants n'ont pas compilé</h2>");
            }
        } catch (IOException e) {
            System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
        }
    }
    /**
     * Calcule le pourcentage de fail pourtout les test d'un mutant
     * @param listeTests
     * @return
     */
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
    
    public void PourcentageMutantMortVivant(ArrayList<Mutant> listeMutant){
        int compteurMutantVivant = 0;
        int compteurMutantMort = 0;

        int pourcentageMutantVivant = 0;
        int pourcentageMutantMort = 0;

        for (Mutant m : listeMutant){
            if(m.getNombreTestFails().equals("0")){
                compteurMutantVivant++;
            }
            else{
                compteurMutantMort++;
            }
        }
        
        int somme = compteurMutantVivant + compteurMutantMort;
        if(listeMutant.size() != 0){
            pourcentageMutantVivant = (compteurMutantVivant*100)/somme;
            pourcentageMutantMort = (compteurMutantMort*100)/somme;
        }
        
        try {
            bw.write("<h2>"+pourcentageMutantMort+"% de mutants sont mort et "+pourcentageMutantVivant+"% des mutant sont vivant apres les tests</h2>");
           // bw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
        
///////////////////////////////// tableau pour chaque class test //////////////////////////////////
    
    /**
     * Generer un partie du tableau des test par class de test : chaque invocation de cette methode traite une class test
     * @param testClass
     * @param nomFichier
     */
    public void genererTableauxTestParClass(TestsParClass testClass, String nomFichier) {
        try {
            ArrayList<Test> listeTests = testClass.getListeTests();

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
    
    /**
     * On ecrire le debut du tableaux des test pas class de test
     * On doit separer cette partie pour pouvoir remplire le tableau avec plusieur appelle de methode.
     * @param nomSerieTest
     */
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
    
    /**
     * On ecrire la fin du tableaux des test pas class de test
     * On doit separer cette partie pour pouvoir remplire le tableau avec plusieur appelle de methode.
     */
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

    
    /**
     * Permet de creer le tableau de mutant mort lors des tests
     * @param listeMutant
     */
    public void genererTableauxMutantMort(ArrayList<Mutant> listeMutant) {
        try {
            bw.write("</div><div><h2>Les mutant Mort</h2>");
            bw.write("<table border>");
            bw.write("<tr>");
            bw.write("<td>Mutant</td> <td>Nombre de tests réussie</td> <td>Nombre de test echoue</td>");
            bw.write("</tr>");
            
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
    
    /**
     * Permet de creer un tableau qui regroupe les mutant en vie et leur mutation (avec position de chaque mutations)
     * @param listeMutant
     */
    public void genererTableauxMutantVivant(ArrayList<Mutant> listeMutant) {
        try {
            
            
            bw.write("</div><div><h2>Les mutant Vivant</h2>");
            
            bw.write("<table class=\"Menu\" border>");
            bw.write("<tr>");
            bw.write("<td class=\"MutantMenu\">Mutant</td><td class=\"MutationMenu\">Mutation</td><td class=\"classMenu\">Class modifie</td><td class=\"MethodeMenu\">Methode modifie</td>");
            bw.write("</tr>");
            bw.write("</table>");

            bw.write("<table border>");
      
            if(listeMutant.size() != 0){
                for(Mutant mut : listeMutant){  
                    if(mut.getNombreTestFails().equals("0")){
                        
                        bw.write("<tr>");
                        bw.write("<td class=\"MutantMenu\">"+mut.getNom()+"</td>");
                        bw.write("<td class=\"tdAvecTab\">");
                        bw.write("<table class=\"tabMutant\"  border>");

                        ArrayList<Mutation> listeMutation = mut.getMutations();
    
                        if(listeMutation.size() != 0){
                            for(int i = 0 ; i < listeMutation.size(); i++){
                                ArrayList<String> listClass = listeMutation.get(i).getListeClass();
                                
                                bw.write("<tr>");
                                bw.write("<td class=\"Mutation\">"+listeMutation.get(i).getNom()+"</td>");
                                bw.write("<td class=\"tdAvecTab\">");
                                bw.write("<table class=\"tabMutation\" border>");
        
                                if(listClass.size() != 0){
                                    for(int y = 0 ; y < listClass.size(); y++){
                                        ArrayList<String> listMethode = listeMutation.get(i).getListeMethode(listClass.get(y));
                                        
                                        bw.write("<tr>");

                                        bw.write("<td class=\"class\">"+listClass.get(y)+"</td>");
                                        bw.write("<td class=\"tdAvecTab\">");
                                        bw.write("<table class=\"tabClass\" border>");
                                        
                                        if(listMethode.size() != 0){
                                            for(int z = 0 ; z < listMethode.size(); z++){            
                                                bw.write("<tr class=\"Methode\"><td>"+listMethode.get(z)+"</td></tr>");

                                            }
                                        }
                                        else{
                                            bw.write("<tr><td class=\"Methode\">Toutes les methodes</td></tr>");

                                        }
                                        bw.write("</table>");
                                        bw.write("</td>");
                                        bw.write("</tr>");

                                    }
                                }
                                else{
                                    bw.write("<tr><td class=\"class\">Toutes les Classes et methodes</td></tr>");
                                }
                                
                                bw.write("</table>");
                                bw.write("</td>");
                                bw.write("</tr>");
        
                            }
                        }
                        
                        bw.write("</table>");
                        bw.write("</td>");
                        bw.write("</tr>");
    
                    }
    
                    
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

    /**
     * On ecrit le debut du fichier html avec un lien vers du css
     */
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
    
    /**
     * On ecrit la fin du fichier html
     */
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
