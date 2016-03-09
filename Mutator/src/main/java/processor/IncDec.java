package processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;


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