package processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class PlusMinus extends AbstractProjectProcessor {

    public PlusMinus() {
        super("PlusMinus");
    }

    @Override
    boolean pecularVerify(CtElement candidate) {
        if ((candidate instanceof CtBinaryOperator)) {
            if(((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.PLUS
                    ||((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.MINUS) {
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
            return verifyClass(candidate.getParent(CtClass.class).getSimpleName());
                  //  && verifyMethod(candidate.getParent(CtMethod.class).getSimpleName());
        }
        return false;
    }

	@Override
	public void process(CtElement candidate) {
        if (((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.PLUS) {
            CtBinaryOperator op = (CtBinaryOperator)candidate;
            op.setKind(BinaryOperatorKind.MINUS);
        }
        else if (((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.MINUS) {
            CtBinaryOperator op = (CtBinaryOperator)candidate;
            op.setKind(BinaryOperatorKind.PLUS);
        }

	}
}