package analyseur.analyse;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Permet de creer un mutant qui contient les mutation et test qui lui sont applique.
 * @author Quentin Laborde
 *
 */
public class Mutant {
    
    private String nom = "";
    
    //Liste des test par class de test
    private ArrayList<TestsParClass> listeClassTest = new ArrayList<TestsParClass>();
    
    //Liste des mutations du mutant qui sont applique sur certain partie du code (voir conf.xml)
    private ArrayList<Mutation> mutations = new ArrayList<Mutation>();

    //Liste des Test effectuer sur tout le mutant
    private ArrayList<Test> listeTest = new ArrayList<Test>();
    
    private String nombreTest;
    private String nombreTestFails;
    private String nombreTestErrors;
    private String nombreTestSkipped;
    
    private ArrayList<String> listeProcessor = new ArrayList<String>();

   
    public Mutant(String nom){
        this.nom = nom;
        
        String tab[] = nom.split(Pattern.quote("+"));
        for(String proc : tab){
            listeProcessor.add(proc);
        }
    }
   
    public void addTestParClass(TestsParClass liste){
        listeClassTest.add(liste);
    }

    public boolean haveProcessor(String proc){
        for(String p : listeProcessor){
            if(p.equals(proc)) return true;
        }
        
        return false;
    }
    
    ////////////////////////////: Getters et setters ///////////////////////////
    
    
    public ArrayList<TestsParClass> getListeClassTest() {
        return listeClassTest;
    }
    
    public void setListeClassTest(ArrayList<TestsParClass> listeClassTest) {
        this.listeClassTest = listeClassTest;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<String> getListeProcessor() {
        return listeProcessor;
    }

    public void setListeProcessor(ArrayList<String> listeProcessor) {
        this.listeProcessor = listeProcessor;
    }

    public ArrayList<Test> getListeTest() {
        return listeTest;
    }

    public void setListeTest(ArrayList<Test> listeTest) {
        this.listeTest = listeTest;
    }

    public String getNombreTest() {
        if(nombreTest == null){
            int nbTest = 0;
            for(TestsParClass t : listeClassTest){
                nbTest += Integer.parseInt(t.getNombreTest());
  
            }
            nombreTest = nbTest + "";

        }
        return nombreTest;
    }

    public void setNombreTest(String nombreTest) {
        this.nombreTest = nombreTest;
    }

    public String getNombreTestFails() {
        if(nombreTestFails == null){
            int nbTest = 0;
            for(TestsParClass t : listeClassTest){
                nbTest += Integer.parseInt(t.getNombreTestFails());
            }
              
            nombreTestFails = nbTest + "";
        }
        return nombreTestFails;
    }

    public void setNombreTestFails(String nombreTestFails) {
        this.nombreTestFails = nombreTestFails;
    }

    public String getNombreTestErrors() {
        if(nombreTestErrors == null){
            int nbTest = 0;
            for(TestsParClass t : listeClassTest){
                nbTest += Integer.parseInt(t.getNombreTestErrors());
            }
          
            nombreTestErrors = nbTest + "";
        }
        return nombreTestErrors;
    }

    public void setNombreTestErrors(String nombreTestErrors) {
        this.nombreTestErrors = nombreTestErrors;
    }

    public String getNombreTestSkipped() {
        if(nombreTestSkipped == null){
            int nbTest = 0;
            for(TestsParClass t : listeClassTest){
                nbTest += Integer.parseInt(t.getNombreTestSkipped());
            }
          
            nombreTestSkipped = nbTest + "";
        }
        return nombreTestSkipped;
    }

    public void setNombreTestSkipped(String nombreTestSkipped) {
        this.nombreTestSkipped = nombreTestSkipped;
    }

    public ArrayList<Mutation> getMutations() {
        return mutations;
    }

    public void setMutations(ArrayList<Mutation> mutations) {
        this.mutations = mutations;
    }
       
    
    
    
    
}
