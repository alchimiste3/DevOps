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

public class LoopInverter extends AbstractProjectProcessor {

    public LoopInverter() {
        super("LoopInverter");
    }

    @Override
    boolean pecularVerify(CtElement candidate) {
        return candidate instanceof CtWhile || candidate instanceof CtFor;
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

	/**Inverse les conditions des boucles, une condition qui était évaluée
         * à false sera maintenant évaluée à true et inversement
         * @param candidate 
         */
	public void process(CtElement candidate) {
                
            CtUnaryOperator op = new CtUnaryOperatorImpl();
            op.setKind(UnaryOperatorKind.NOT);
            
		if (candidate instanceof CtWhile) {
                    CtWhile var = (CtWhile)candidate;
                    CtExpression condition = var.getLoopingExpression();
                    Factory factory = var.getFactory();
                    op.setFactory(factory);
                    op.setOperand(condition);
                    var.setLoopingExpression(op);
                }
                else if (candidate instanceof CtFor)
                {
                    CtFor var = (CtFor)candidate;
                    CtExpression condition = var.getExpression();
                    Factory factory = var.getFactory();
                    op.setFactory(factory);
                    op.setOperand(condition);
                    var.setExpression(op);
                }
                else
                    return;
	}
}
