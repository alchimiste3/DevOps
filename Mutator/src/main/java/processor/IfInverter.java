package processor;

import javax.management.openmbean.SimpleType;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
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
        CtPackage p = candidate.getParent(CtPackage.class);
        CtClass c = candidate.getParent(CtClass.class);
        CtMethod m = candidate.getParent(CtMethod.class);
        try {
            if(pecularVerify(candidate)){
                if(verifyPackage(p.getSimpleName())){
                    if(verifyClass(c.getSimpleName())){
                        if(verifyMethod(m.getSignature())){
                            return verifyNbApplication();
                        }
                    }
                }
            }

        }
        catch(NullPointerException e) {
            return false;
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