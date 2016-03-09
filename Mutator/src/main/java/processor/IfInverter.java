package processor;

import javax.management.openmbean.SimpleType;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.Factory;
import spoon.support.reflect.code.CtUnaryOperatorImpl;

public class IfInverter extends AbstractProjectProcessor {

    public IfInverter() {
        super("IfInverter");
    }

    @Override
    boolean pecularVerify(CtElement candidate) {
        return candidate instanceof CtIf;
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

	
	public void process(CtElement candidate) {
		if (!(candidate instanceof CtIf)) {
			return;
		}
		CtIf var = (CtIf)candidate;
                CtExpression condition = var.getCondition();
                Factory factory = var.getFactory();
                CtUnaryOperator op = new CtUnaryOperatorImpl();
                op.setFactory(factory);
                op.setOperand(condition);
                op.setKind(UnaryOperatorKind.NOT);
                var.setCondition(op);
	}
}
