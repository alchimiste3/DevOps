package processor;

import javax.management.openmbean.SimpleType;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.ClassFactory;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.declaration.CtTypeImpl;

public class DoubleMutator extends AbstractProjectProcessor {

    public DoubleMutator() {
        super("DoubleMutator");
    }

    @Override
    boolean pecularVerify(CtElement candidate) {
        return candidate instanceof CtLocalVariable;
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

	
	public void process(CtElement candidate) {
		if (!(candidate instanceof CtLocalVariable)) {
			return;
		}
		CtLocalVariable var = (CtLocalVariable)candidate;
                ClassFactory doubleRef = new ClassFactory(var.getFactory());
                if(var.getType().getSimpleName().equals("double") )
                    var.setType(doubleRef.INTEGER_PRIMITIVE);
               // if (var.getSimpleName().equals("float"));
               //     var.setSimpleName("int");
	}
}
