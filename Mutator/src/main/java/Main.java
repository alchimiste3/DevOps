
import processor.BinaryOperatorMutator;

/**
 * Created by sualty on 12/02/16.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Aucune source passée en paramètre");
        }
        else {
            System.out.println("Application de la mutation sur le fichier" + args[0]);
            BinaryOperatorMutator mutationOperator = new BinaryOperatorMutator();
        }
        
    }
}
