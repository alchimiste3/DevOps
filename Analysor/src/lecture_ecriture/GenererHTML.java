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
            bw.write("<html>");
            bw.write("<head><title>Resultats</title></head>");
            bw.write("<body>");
        }
        catch(IOException e) {
            System.out.println("Impossible de creer le fichier de resultats" + e.getStackTrace());
        }
    }

        public void genererListeTests(ArrayList<Test> listeTests, String nomFichier) {
            try {
            int nbFails = 0;
            int nbTests = 0;
            for (Test test : listeTests)
            {
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
    } catch(IOException e)
    {
        System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
    }
        }
        
        public void genererFin() {
            try {
                bw.write("</body>");
                bw.append("</html>");
                bw.close();
            } catch(IOException e) {
                System.out.println("Impossible d'écrire dans le fichier" + e.getStackTrace());
            }
            
        }
}
