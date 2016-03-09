package processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;


public class GELE extends AbstractProjectProcessor {


    public GELE() {
        super("GELE");
    }


    public boolean pecularVerify(CtElement candidate) {
        if ((candidate instanceof CtBinaryOperator)) {
            if(((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.LE
                    ||((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.GE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dans cette méthode on vérifie si la classe/méthode de l'élément analysé correspondent
     * et si la vérification particulière correspond.On retourne le résultat sou forme de boolean
     * @param candidate l'élément analysé
     * @return résultat de la vérification
     */
    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        if(pecularVerify(candidate)) {
            if(verifyClass(candidate.getParent(CtClass.class).getSimpleName())){
                return verifyNbApplication();
            }
        }
        return false;
    }

    @Override
    public void process(CtElement candidate) {
        if(((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.LE){
            CtBinaryOperator op = (CtBinaryOperator)candidate;
            op.setKind(BinaryOperatorKind.GE);
        }
        else if(((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.GE){
            CtBinaryOperator op = (CtBinaryOperator)candidate;
            op.setKind(BinaryOperatorKind.LE);
        }

    }
}