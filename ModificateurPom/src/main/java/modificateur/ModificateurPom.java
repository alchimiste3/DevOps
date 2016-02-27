package modificateur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;  
import org.jdom2.Element;  
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ModificateurPom {
    
    File file;
    Document document;
    Namespace namespace = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");

    public void modifierProcessors(ArrayList<String> listeProc, String pathPom){
        System.out.println("salut");
        SAXBuilder saxBuilder = new SAXBuilder();  
        
        file = new File(pathPom);  
        
     
        try {  
            
            document = saxBuilder.build(file);  
            Element rootNode = document.getRootElement();      
            Element build = rootNode.getChild("build", namespace);
            Element plugins = build.getChild("plugins", namespace);
            List<Element> listePlugin = plugins.getChildren();
            Element configuration = listePlugin.get(1).getChild("configuration", namespace);
            Element processors = configuration.getChild("processors", namespace);
            
            supprimerProc(processors);

            ajouterProc(processors,listeProc);
           
            sauvagarder();
            
        }
        catch(Exception e){
            System.out.println("Probleme ecriture processor → "+e);
        }
        
    }
    
    private void sauvagarder() throws IOException{
        // On sauvegarde les modification 
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        outputter.output(document, System.out);
    }
    
    private void supprimerProc(Element processors){
        processors.removeContent();
    }
    
    private void ajouterProc(Element processors, ArrayList<String> listeProc){
        for(String proc : listeProc){
            System.out.println("salutqeeffzef");
            Element newProc = new Element("processor", namespace);
            newProc.setText("processor."+proc);
            processors.addContent(newProc);

        }
    }
}  