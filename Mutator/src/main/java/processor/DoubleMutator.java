package processor;

import javax.management.openmbean.SimpleType;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.ClassFactory;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.declaration.CtTypeImpl;

public class DoubleMutator extends AbstractProcessor<CtElement> {
	@Override
	public boolean isToBeProcessed(CtElement candidate) {
		return candidate instanceof CtLocalVariable;
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
