package analyseur.lecture_ecriture;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;  
import org.jdom2.Element;  
import org.jdom2.input.SAXBuilder;

import analyseur.analyse.Mutant;
import analyseur.analyse.Mutation;
import analyseur.analyse.Test;
import analyseur.analyse.TestsParClass;

/**
 * Permet de lire les fichiers xml conf.xml, listeMutant.xml et les fichiers xml generaient par Junit
 * @author user
 *
 */
public class LireXML {

    /**
     * Lit les fichier xml test de Junit
     * @param path
     * @return
     */
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
    

    /**
     * Lit les mutants sur un fichier xml et creer une liste de Mutant qu'il retourne
     * @param pathMutantXML
     * @param pathConfXML
     * @return
     */
    public ArrayList<Mutant> lireMutant(String pathMutantXML, String pathConfXML){  
        ArrayList<Mutant> listeMutant = new ArrayList<Mutant>();

        SAXBuilder saxBuilder = new SAXBuilder();  
      
        File fileMutants = new File(pathMutantXML);  
        File fileConf = new File(pathConfXML);  

        try {  
            Document documentMutants = saxBuilder.build(fileMutants);  

            Element rootNode = documentMutants.getRootElement(); 
            
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
                mutant.setListeProcessor(listeProc);
                // On recupere les test
                Element tests = mut.getChild("tests");

                creerTest(tests, mutant);

                listeMutant.add(mutant);
            }

        } catch (Exception e) {  
            System.out.println("Probleme lecture XML Mutant → "+e);
        }  

        lireConfMutantXML(listeMutant, saxBuilder, fileConf);
     
        
        return listeMutant;
    }
    
    /**
     * Ajoute une liste de Test a un Mutant
     * @param tests
     * @param mutant
     */
    private void creerTest(Element tests, Mutant mutant){
        ArrayList<Test> listeTest = new ArrayList<Test>();

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
    }
    
    
    /**
     * Lit la configuration des mutants dans le fichier conf.xml et modifier les Mutant
     * @param listeMutant
     * @param saxBuilder
     * @param fileConf
     */
    private void lireConfMutantXML(ArrayList<Mutant> listeMutant, SAXBuilder saxBuilder, File fileConf){
     // Quant tout les mutant sont creer, on récupere les mutation dans le fichier conf
        try {
            
            Document documentConf = saxBuilder.build(fileConf);  
            Element rootNode = documentConf.getRootElement(); 
            
            Element processors = rootNode.getChild("processors");
            List<Element> listeProc = processors.getChildren("processor");

            for(Mutant m : listeMutant){               
                for(Element proc : listeProc){
                    if(m.haveProcessor(proc.getChildText("nom"))){
                        creerMutation(proc, m);   
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("Probleme lecture XML conf → "+e);
        }
    }
    
    /**
     * Creer une mutation à ajouter à un Mutant
     * @param proc
     * @param mut
     */
    private void creerMutation(Element proc,Mutant mut){
        Mutation mutation = new Mutation();
        
        mutation.setNom(proc.getChildText("nom"));
                            
        List<Element> listClass = proc.getChildren("classe");
        
        for(Element c : listClass){
            String nomClass = c.getChildText("nom");
            mutation.addClass(nomClass);
            
            List<Element> listMethode = c.getChildren("methode");
            for(Element m : listMethode){
                mutation.addMethode(nomClass, m.getText());
            }
        }
        
        
        mut.getMutations().add(mutation);
           
    }  
    
    
    
    /**
     * Lit les processors pour creer les mutant dans le fichier conf.xml et modifier le fichier processors.txt
     * @param listeMutant
     * @param saxBuilder
     * @param fileConf
     * @throws IOException 
     * 
     * 
     * 
     */
    public void lireProcessors(String pathConfXML, String pathOfProcTxt){  

        SAXBuilder saxBuilder = new SAXBuilder();  
      
        File fileConf = new File(pathConfXML); 
        
        try {
            
            File fichierProc = new File(pathOfProcTxt);
            fichierProc.createNewFile();
            FileWriter fichier = new FileWriter(fichierProc);
            Document documentConf = saxBuilder.build(fileConf);  
            Element rootNode = documentConf.getRootElement(); 
            
            Element mutants = rootNode.getChild("mutants");
            List<Element> listemutant = mutants.getChildren("mutant");

            for(Element mutant : listemutant){
                List<Element> listeproc = mutant.getChildren("processor");
                
                String res = "";
                for(int i =0;i<listeproc.size();i++){
                    Element proc = listeproc.get(i);
                    if(i==0)
                        res = res+proc.getText();
                    else
                        res = res + " " + proc.getText();
                }
                fichier.write(res + "\n");

            }
            
            fichier.close();
            
        } catch (Exception e) {
            System.out.println("Probleme lecture XML conf → "+e);
        }
    }
    
}   