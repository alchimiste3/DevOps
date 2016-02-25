package processor;

import spoon.processing.AbstractProcessor;
import spoon.processing.ProcessingManager;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtElement;


public class GELE extends AbstractProcessor<CtElement> {

    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        return candidate instanceof CtBinaryOperator;
    }

    @Override
    public void process(CtElement candidate) {
        if (!(candidate instanceof CtBinaryOperator && ((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.LE)) {
            return;
        }
        CtBinaryOperator op = (CtBinaryOperator)candidate;
        op.setKind(BinaryOperatorKind.GE);
    }
}