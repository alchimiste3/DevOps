package modificateurPom.main;

import modificateurPom.modificateur.ModificateurPom;

import java.util.ArrayList;

public class Main {

    /**
     * 
     * @param args contient les noms des procs qui doivent etre écrie dans le pom
     */
    public static void main(String[] args) {
        
        String pathPom = "/home/sualty/Bureau/DEVOPS/V1.1/DevOps/SourcesUnderTest/pom.xml";
        ArrayList<String> listeProc = new ArrayList<String>();
        
        for(String proc : args){
            listeProc.add(proc);
        }
        
        //listeProc.add("eqded");

        ModificateurPom modificateur = new ModificateurPom();
        modificateur.modifierProcessors(listeProc, pathPom);

    }
}
