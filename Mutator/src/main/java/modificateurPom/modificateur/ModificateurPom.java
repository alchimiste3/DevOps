package modificateurPom.modificateur;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModificateurPom {
    
    private File file;
    private Document document;
    private Namespace namespace = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");

    public void modifierProcessors(ArrayList<String> listeProc, String pathPom){
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
           
            sauvagarder(pathPom);
            
        }
        catch(Exception e){
            System.out.println("Probleme ecriture processor → "+e);
        }
        
    }
    
    private void sauvagarder(String pathPom) throws IOException{
        // On sauvegarde les modification
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        outputter.output(document, new FileOutputStream(pathPom));
    }
    
    private void supprimerProc(Element processors){
        processors.removeContent();
    }
    
    private void ajouterProc(Element processors, ArrayList<String> listeProc){
        for(String proc : listeProc){
            Element newProc = new Element("processor", namespace);
            newProc.setText("processor."+proc);
            processors.addContent(newProc);

        }
    }
}  