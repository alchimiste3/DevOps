package analyse;

import java.io.*;
import org.jdom.*;
import org.jdom.input.*;
import org.w3c.dom.Document;
import org.jdom.filter.*;
import java.util.List;

import javax.xml.bind.Element;

import java.util.Iterator;


public class Analyseur {
    
    private String furefirePath = "surefire-reports/";
    static Document document;
    static Element racine;

    public Analyseur() {
        readXMLFile("TEST-fr.inria.gforge.spoon.bound.BoundTest.xml");
    }
    
    public void readXMLFile(String nomFichier) {


        try {
            File fXmlFile = new File(furefirePath + nomFichier);
            SAXBuilder sxb = new SAXBuilder();
            document = sxb.build(new File("Exercice2.xml"));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
          

          
    
    }

}
