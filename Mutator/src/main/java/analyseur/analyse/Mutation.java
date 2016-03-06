package analyseur.analyse;

import java.util.ArrayList;
import java.util.HashMap;

public class Mutation {
    
    private String nom;
    
    //Liste chaque package modifie par le mutation avec les class et les methode toucher par cette mutation
    //HashMap<package,HashMap<class, ArrayList<methode>> >
    private HashMap<String,HashMap<String, ArrayList<String>> > listeClassMethodes = new HashMap<String, HashMap<String,ArrayList<String>>>();
    
    public void addPackage(String p){
        listeClassMethodes.put(p, new HashMap<String, ArrayList<String>>());
    }
    
    public void addClass(String p,String c){
        listeClassMethodes.get(p).put(c,new ArrayList<String>());
    }
    
    public void addMethode(String p,String c, String m){
        listeClassMethodes.get(p).get(c).add(m);
    }
    
    public HashMap<String, ArrayList<String>> getListeClass(String pack){
        return listeClassMethodes.get(pack);
    }
    
    public ArrayList<String> getListeMethode(String p, String c){
        return listeClassMethodes.get(p).get(c);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public HashMap<String, HashMap<String, ArrayList<String>>> getListeClassMethodes() {
        return listeClassMethodes;
    }

    public void setListeClassMethodes(
            HashMap<String, HashMap<String, ArrayList<String>>> listeClassMethodes) {
        this.listeClassMethodes = listeClassMethodes;
    }
    
    
}
