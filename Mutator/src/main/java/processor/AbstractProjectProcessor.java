package processor;

import analyseur.analyse.Mutant;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

import java.io.File;
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
            if (c.equals(classe)) {
                return true;
            }
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

    public boolean verifyNbApplication() {
        if(this.nb_applications!=0) {
            this.nb_applications--;
            return true;
        }
        return false;
    }

    /**
     * récupère la liste des classes où appliquer le processeur.Si cette liste est vide, on l'applique dans toutes les classes
     * @return la liste des classes où appliquer le processeur
     */
    public void setClasses() {
        this.classes=new ArrayList<String>();
        String path = new java.io.File("").getAbsolutePath();
        path=path.substring(0,path.lastIndexOf('/'));
        path=path+"/conf.xml";

        SAXBuilder saxBuilder = new SAXBuilder();
        File fileConf = new File(path);
        Element config = null;
        try {
            Document documentConf = saxBuilder.build(fileConf);
            Element rootNode = documentConf.getRootElement();

            Element processors = rootNode.getChild("processors");

            List<Element> configs = processors.getChildren("processor");

            for(Element c : configs) {
                if(c.getChildText("nom").equals(this.nom))
                    config = c;
            }
            List<Element> classes = config.getChildren("classe");
            for(Element e : classes) {
                this.classes.add(e.getChildText("nom"));
            }

        }
        catch (Exception e) {
            if(e instanceof NullPointerException) {
                System.out.println("Pas de configuration prévue.Application à toutes les classes");
                e.printStackTrace();
            }
        }
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
        this.nb_applications = -1;
        String path = new java.io.File("").getAbsolutePath();
        path=path.substring(0,path.lastIndexOf('/'));
        path=path+"/conf.xml";

        SAXBuilder saxBuilder = new SAXBuilder();
        File fileConf = new File(path);
        Element config = null;
        try {
            Document documentConf = saxBuilder.build(fileConf);
            Element rootNode = documentConf.getRootElement();

            Element processors = rootNode.getChild("processors");

            List<Element> configs = processors.getChildren("processor");
            for(Element c : configs) {
                if(c.getChildText("nom").equals(this.nom))
                    config = c;
            }
            String nb_str = config.getChildText("applications");
            this.nb_applications = Integer.parseInt(nb_str);
        }
        catch (Exception e) {
            if(e instanceof NullPointerException) {
                e.printStackTrace();
            }
        }
    }
}