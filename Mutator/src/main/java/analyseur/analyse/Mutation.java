package analyseur.analyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Pemret de creer une mutation qui est applique sur un processor
 * @author Quentin Laborde
 *
 */
public class Mutation {
    
    private String nom;
    private int nbMethode = 0;
    
    //Liste chaques class et les methode toucher par cette mutation
    //HashMap<class, ArrayList<methode>>
    private HashMap<String, ArrayList<String>>  listeClassMethodes = new HashMap<String,ArrayList<String>>();
    
    
    public void addClass(String c){
        listeClassMethodes.put(c,new ArrayList<String>());
    }
    
    public void addMethode(String c, String m){
        listeClassMethodes.get(c).add(m);
        nbMethode++;
    }
    
    public ArrayList<String> getListeClass(){
        ArrayList<String> liste = new ArrayList<String>();
        liste.addAll(listeClassMethodes.keySet());
        return liste;
    }
    
    
    public ArrayList<String> getListeMethode(String c){
        return listeClassMethodes.get(c);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public HashMap<String, ArrayList<String>> getListeClassMethodes() {
        return listeClassMethodes;
    }

    public void setListeClassMethodes(
            HashMap<String, ArrayList<String>> listeClassMethodes) {
        this.listeClassMethodes = listeClassMethodes;
    }

    
    ////////////////////////// methode pour construire le html ///////////////////////////
    


    public int getNbMethode() {
        return nbMethode;
    }


    public int getNbMethodeParClass(String p,String c) {
        HashMap<String, ArrayList<String>> listeCLass = getListeClassMethodes();
        return listeCLass.get(c).size();

    }
    
}
