package analyseur.lecture_ecriture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import analyseur.analyse.Mutant;
import analyseur.analyse.Test;
import analyseur.analyse.TestsParClass;

public class EcrireXML {
    
    private File file;
    private Document document;

    public void ecrireMutant(Mutant mutant, String path){
        SAXBuilder saxBuilder = new SAXBuilder();  
        
        file = new File(path);  
        
     
        try {  
            
            document = saxBuilder.build(file); 
            
            Element mutants = document.getRootElement();    

            ajouterMutant(mutant, mutants);
            

            
            sauvagarder(path);
            
        }
        catch(Exception e){
            System.out.println("Probleme ecriture mutant → "+e);
        }
        
    }
    
    private void sauvagarder(String pathPom) throws IOException{
        // On sauvegarde les modification
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        outputter.output(document, new FileOutputStream(pathPom));
    }
    
    
    public void viderXMLMutants(String path){
        SAXBuilder saxBuilder = new SAXBuilder();  
        
        file = new File(path);  

        try {  
            document = saxBuilder.build(file); 
            
            Element rootNode = document.getRootElement();      

            rootNode.removeContent();  
            
            sauvagarder(path);
            
        }
        catch(Exception e){
            System.out.println("Probleme vider mutant → "+e.getLocalizedMessage());
        }
        
    }
    
    private void ajouterMutant(Mutant mutant, Element mutants){
        

        Element mut = new Element("mutant");
        Element nomMutant = new Element("nom");
        nomMutant.setText(mutant.getNom());
        mut.addContent(nomMutant);
        
        Element nbTest = new Element("nbTest");
        nbTest.setText(mutant.getNombreTest()+"");
        mut.addContent(nbTest);
        
        Element nbTestFail = new Element("nbTestFail");
        nbTestFail.setText(mutant.getNombreTestFails()+"");
        mut.addContent(nbTestFail);
        
        Element nbTestError = new Element("nbTestError");
        nbTestError.setText(mutant.getNombreTestErrors()+"");
        mut.addContent(nbTestError);
        
        Element nbTestSkipped = new Element("nbTestSkipped");
        nbTestSkipped.setText(mutant.getNombreTestSkipped()+"");
        mut.addContent(nbTestSkipped);

        
        Element processors = new Element("processors");
        
        for(String proc : mutant.getListeProcessor()){
            Element processor = new Element("processor");
            processor.setText(proc);
            processors.addContent(processor);
           
        }
        
        mut.addContent(processors);
        
        Element tests = new Element("tests");

        for(TestsParClass listetest : mutant.getListeClassTest()){
            for(Test t : listetest.getListeTests()){
                Element test = new Element("test");
    
                Element nomClassTest = new Element("nomClassTest");
                nomClassTest.setText(t.getNomClass());
                test.addContent(nomClassTest);
                
                Element nom = new Element("nom");
                nom.setText(t.getNom());
                test.addContent(nom);
                  
                Element fail = new Element("fail");
                if(t.isFail()){
                    fail.setText("true");
                }
                else{
                    fail.setText("false");
                }
                test.addContent(fail);               
                
                Element typeFail = new Element("typeFail");
                typeFail.setText(t.getTypeFail());
                test.addContent(typeFail);
                
//                private String typeFail;
//                
                tests.addContent(test); 
                
            }

        }
        
        mut.addContent(tests);

        
        mutants.addContent(mut);

    }
}
