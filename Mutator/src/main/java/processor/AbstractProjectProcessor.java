package processor;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sualty on 06/03/16.
 */
abstract class AbstractProjectProcessor extends spoon.processing.AbstractProcessor<CtElement> {
    protected String nom;
    protected List<String> classes;
    protected List<String> methodes;
    protected int nb_applications;

    public AbstractProjectProcessor(String nom) {
        super();
        this.nom = nom;
        setClasses();
        setMethodes();
        setNbApplication();
    }

    /**
     * Vérification qui sera définie dans les processeurs héritant de cette classe
     * @return le résultat de la vérification
     */
    abstract boolean pecularVerify(CtElement candidate);

    /**
     *vérifie si une classe est dans la liste des classes
     * @param classe la classe à vérifier
     * @return true or false
     */
    public boolean verifyClass(String classe) {
        if(classes.size()==0)
            return true;

        for(String c:classes) {
            if (c.equals(classe))
               return true;
        }
        return false;
    }

    /**
     *vérifie si une méthode est dans la liste des méthodes
     * @param method la méthode à vérifier
     * @return true or false
     */
    public boolean verifyMethod(String method) {
        if(methodes.size()==0)
            return true;

        for(String m: methodes) {
            if (m.equals(method))
                return true;
            }
        return false;
    }
    /**
     * récupère la liste des classes où appliquer le processeur.Si cette liste est vide, on l'applique dans toutes les classes
     * @return la liste des classes où appliquer le processeur
     */
    public void setClasses() {
        List<String> list = new ArrayList<String>();
        //TODO:récupérer info du xml si existante . On utilise la variable "nom" pour savoir où chercher
        this.classes=list;
    }

    /**
     * récupère la liste des méthodes où appliquer le processeur.Si cette liste est vide, on l'applique dans toutes les méthodes
     * @return la liste des méthodes où appliquer le processeur
     */
    public void setMethodes() {
        List<String> list = new ArrayList<String>();
        //TODO:récupérer info du xml si existante . On utilise la variable "nom" pour savoir où chercher
        this.methodes =list;
    }

    /**
     * récupère le nombre de fois à appliquer le processeur.Si -1, on l'applique tout le temps
     * @return le nombre de fois à appliquer le processeur
     */
    public void setNbApplication() {
        int nb=-1;
        //TODO:récupérer info du xml si existante . On utilise la variable "nom" pour savoir où chercher
        this.nb_applications = nb;
    }
}