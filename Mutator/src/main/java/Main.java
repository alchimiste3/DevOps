
import processor.BinaryOperatorMutator;

/**
 * Created by sualty on 12/02/16.
 */
public class Main {
    public static void main(String[] args) {
        String src = "/home/user/Documents/DevOps/DevOps/Mutator/src/main/java/MaPremiereClasse.java";
      //  if (args.length < 1) {
            System.out.println("Aucune source passée en paramètre");
      //  }
      //  else {
            System.out.println("Application de la mutation sur le fichier" + src);
            BinaryOperatorMutator mutationOperator = new BinaryOperatorMutator();
            MutationTester mutationTester = new MutationTester(src, mutationOperator);
            mutationTester.generateMutants();
      //  }
        
    }
}
