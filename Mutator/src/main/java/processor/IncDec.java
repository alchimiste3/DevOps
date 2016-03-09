package processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;


public class IncDec extends AbstractProjectProcessor {

    public IncDec() {
        super("IncDec");
    }

    @Override
    boolean pecularVerify(CtElement candidate) {
        if ((candidate instanceof CtUnaryOperator)) {
            if(((CtUnaryOperator)candidate).getKind()== UnaryOperatorKind.POSTINC
                    ||((CtUnaryOperator)candidate).getKind()==UnaryOperatorKind.POSTDEC) {
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
        if (!(candidate instanceof CtUnaryOperator)) {
            return;
        }
        else if(((CtUnaryOperator)candidate).getKind()==UnaryOperatorKind.POSTINC) {
            CtUnaryOperator op = (CtUnaryOperator)candidate;
            op.setKind(UnaryOperatorKind.POSTDEC);
        }
        else if(((CtUnaryOperator)candidate).getKind()==UnaryOperatorKind.POSTDEC) {
            CtUnaryOperator op = (CtUnaryOperator)candidate;
            op.setKind(UnaryOperatorKind.POSTINC);
        }

    }
}