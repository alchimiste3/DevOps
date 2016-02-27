package main;

import java.util.ArrayList;

import modificateur.ModificateurPom;

public class Main {

    /**
     * 
     * @param args contient les noms des procs qui doivent etre écrie dans le pom
     */
    public static void main(String[] args) {
        
        String pathPom = "pom_test.xml";
        ArrayList<String> listeProc = new ArrayList<String>();
        
        for(String proc : args){
            listeProc.add(proc);
        }
        
        //listeProc.add("eqded");

        ModificateurPom modificateur = new ModificateurPom();
        modificateur.modifierProcessors(listeProc, pathPom);

    }
}
