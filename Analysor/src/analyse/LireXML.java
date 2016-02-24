package analyse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;  
import org.jdom2.Element;  
import org.jdom2.JDOMException;  
import org.jdom2.input.SAXBuilder;  
public class LireXML {

    
    public LireXML() {
        
    }

    public ArrayList<Test> lireTest(String path){  
        
        ArrayList<Test> listTest = new ArrayList<>();
        
         SAXBuilder saxBuilder = new SAXBuilder();  
       
         File file = new File(path);  
      
         try {  
             Document document = saxBuilder.build(file);  
         
             Element rootNode = document.getRootElement(); 
                      
             List<Element> listeTest = rootNode.getChildren("testcase");  
        
             
             for(Element e : listeTest){
                 Test test = new Test(e.getAttributeValue("name"), e.getAttributeValue("classname"));
                 System.out.println("fail : "+e.getChild("failure"));
                 if(!e.getChildText("failure").equals(null)){
                     test.setFail(true);
                     test.setTypeFail(e.getChild("failure").getAttributeValue("type"));
                 }
                 
                 listTest.add(test);
             }

         } catch (Exception e) {  
                 e.printStackTrace();  
         }  
         
         return listTest;
     }
}  