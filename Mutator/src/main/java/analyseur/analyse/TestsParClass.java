package analyseur.analyse;

import java.util.ArrayList;

/**
 * Permet de creer un TestsParClass qui contient touts les objet Test d'une class test.
 * @author Quentin Laborde
 *
 */
public class TestsParClass {
    ArrayList<Test> listeTests = new ArrayList<Test>();
    
    private String nombreTest;
    private String nomClass;
    private String tempsExecution;
    private String nombreTestFails;
    private String nombreTestErrors;
    private String nombreTestSkipped;
    
    
    public TestsParClass(){
        
    }
    
    
    public void addTest(Test test){
        listeTests.add(test);
    }
    
    
    //////////////////////////////////// Getters et Setters ///////////////////////////////////
    
    
    public ArrayList<Test> getListeTests() {
        return listeTests;
    }
    public void setListeTests(ArrayList<Test> listeTests) {
        this.listeTests = listeTests;
    }
    public String getNombreTest() {
        return nombreTest;
    }
    public void setNombreTest(String nombreTest) {
        this.nombreTest = nombreTest;
    }
    public String getNomClass() {
        return nomClass;
    }
    public void setNomClass(String nomClass) {
        this.nomClass = nomClass;
    }
    public String getTempsExecution() {
        return tempsExecution;
    }
    public void setTempsExecution(String tempsExecution) {
        this.tempsExecution = tempsExecution;
    }
    public String getNombreTestFails() {
        return nombreTestFails;
    }
    public void setNombreTestFails(String nombreTestFails) {
        this.nombreTestFails = nombreTestFails;
    }
    public String getNombreTestErrors() {
        return nombreTestErrors;
    }
    public void setNombreTestErrors(String nombreTestErrors) {
        this.nombreTestErrors = nombreTestErrors;
    }
    public String getNombreTestSkipped() {
        return nombreTestSkipped;
    }
    public void setNombreTestSkipped(String nombreTestSkipped) {
        this.nombreTestSkipped = nombreTestSkipped;
    }
    
    
    

}
