package analyse;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import javax.xml.bind.Element;

import java.util.ArrayList;
import java.util.Iterator;


public class Analyseur {
    
    private String furefirePath = "surefire-reports/";

    public Analyseur(){
        
    }
    
    public void AnalyserFichiersTests(String nom){
        LireXML parser = new LireXML();
        ArrayList<Test> listTest = parser.lireTest(furefirePath+nom);
        
        System.out.println(listTest.size());
        for(Test t : listTest){
            System.out.println(t.display());
        }

    }
    
}
