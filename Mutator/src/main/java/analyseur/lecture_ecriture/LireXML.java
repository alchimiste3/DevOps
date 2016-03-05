package analyseur.lecture_ecriture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;  
import org.jdom2.Element;  
import org.jdom2.JDOMException;  
import org.jdom2.input.SAXBuilder;

import analyseur.analyse.Mutant;
import analyseur.analyse.Test;
import analyseur.analyse.TestsParClass;
public class LireXML {

    
    public LireXML() {
        
    }

    public TestsParClass lireTests(String path){  
        
        TestsParClass testClass = new TestsParClass();
                
         SAXBuilder saxBuilder = new SAXBuilder();  
       
         File file = new File(path);  
      
         try {  
             Document document = saxBuilder.build(file);  
         
             Element rootNode = document.getRootElement(); 
             
             
             //On recupere les imformation general sur le deroulement des tests de la class test.
             testClass.setNombreTest(rootNode.getAttributeValue("tests"));
             testClass.setNomClass(rootNode.getAttributeValue("name"));
             testClass.setTempsExecution(rootNode.getAttributeValue("time"));
             testClass.setNombreTestFails(rootNode.getAttributeValue("failures"));
             testClass.setNombreTestErrors(rootNode.getAttributeValue("errors"));
             testClass.setNombreTestSkipped(rootNode.getAttributeValue("skipped"));
             
                                  
             List<Element> listeTest = rootNode.getChildren("testcase");  
        
             
             for(Element e : listeTest){
                 Test test = new Test(e.getAttributeValue("name"), e.getAttributeValue("classname"));
                 
                 if(e.getChildText("failure") != null){
                     test.setFail(true);
                     test.setTypeFail(e.getChild("failure").getAttributeValue("type"));
                 }
                 else{
                     test.setFail(false);
                 }

                 
                 testClass.addTest(test);
             }
  

         } catch (Exception e) {  
                 e.printStackTrace();  
         }  
         
         
         
         
         return testClass;
     }
    
    public ArrayList<Mutant> lireMutant(String path){  
        ArrayList<Mutant> listeMutant = new ArrayList<Mutant>();

        SAXBuilder saxBuilder = new SAXBuilder();  
      
        File file = new File(path);  
     
        try {  
            Document document = saxBuilder.build(file);  
        
            Element rootNode = document.getRootElement(); 
            

            List<Element> listeMutantXML = rootNode.getChildren("mutant");
            
            
            // Pour chaque mutant
            for(Element mut : listeMutantXML){
                
                //On recupere le nom
                Mutant mutant = new Mutant(mut.getChildText("nom"));
                
                
                mutant.setNombreTest(mut.getChildText("nbTest"));
                mutant.setNombreTestFails(mut.getChildText("nbTestFail"));
                mutant.setNombreTestErrors(mut.getChildText("nbTestError"));
                mutant.setNombreTestSkipped(mut.getChildText("nbTestSkipped"));
                
                // On recupere les processors
                ArrayList<String> listeProc = new ArrayList<String>();
                Element processors = mut.getChild("processors");
                for(Element proc : processors.getChildren("processor")){
                    listeProc.add(proc.getText());
                }
                
                // On recupere les test
                ArrayList<Test> listeTest = new ArrayList<Test>();
                Element tests = mut.getChild("tests");
                for(Element test : tests.getChildren("test")){
                    
                    Test t = new Test(test.getChildText("nom"), test.getChildText("nomClassTest"));
                    
                    if(test.getChildText("fail").equals("true")){
                        t.setFail(true);
                    }
                    else{
                        t.setFail(false);      
                    }
                    
                    t.setTypeFail(test.getChildText("typeFail"));
                    
                    listeTest.add(t);

                }
                mutant.setListeTest(listeTest);
                
                System.out.println(mutant.getListeTest());
                
                listeMutant.add(mutant);
            }
            
           

        } catch (Exception e) {  
                e.printStackTrace();  
        }  
        
        
        
        
        return listeMutant;
    }
}  