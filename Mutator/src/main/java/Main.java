import java.io.File;
import java.io.IOException;

import processor.BinaryOperatorMutator;






/**
 * Created by sualty on 12/02/16.
 */
public class Main {
    
    private String pk = "/src/main/java/packageTest";
    
    private String cheminMutator(){
        
        try {
            return new File(".").getCanonicalPath();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "";
    }
    
    
    
    
    
    public String getPk() {
        return pk;
    }





    public static void main(String[] args) {
        
        Main main = new Main();

        
        String src = main.cheminMutator() + main.getPk() ;
        
//        if (args.length < 1) {
//            System.out.println("Aucune source passée en paramètre");
//        }
//        else {
        
            System.out.println("Application de la mutation sur le fichier" + src + "/MaPremiereClasse.java");
            
            BinaryOperatorMutator mutationOperator = new BinaryOperatorMutator();
            
            MutationTester mutationTester = new MutationTester(src + "/MaPremiereClasse.java", mutationOperator);
            
            mutationTester.generateMutants(src);
            
      //  }
        
    }
}
