package modificateurPom.main;

import modificateurPom.modificateur.ModificateurPom;

import java.util.ArrayList;

public class Main {

    /**
     * 
     * @param args contient les noms des procs qui doivent etre écrie dans le pom
     */
    public static void main(String[] args) {
        
        String pathPom = args[0];
        ArrayList<String> listeProc = new ArrayList<String>();
        
        for(int i=0;i<args.length;i++){
            listeProc.add(args[i]);
        }
        
        //listeProc.add("eqded");

        ModificateurPom modificateur = new ModificateurPom();
        modificateur.modifierProcessors(listeProc, pathPom);

    }
}
