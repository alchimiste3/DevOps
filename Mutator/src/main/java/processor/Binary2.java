package processor;

import spoon.processing.AbstractProcessor;
import spoon.processing.ProcessingManager;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtElement;

public class Binary2 extends AbstractProcessor<CtElement> {

    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        return candidate instanceof CtBinaryOperator;
    }

    @Override
    public void process(CtElement candidate) {
        if (!(candidate instanceof CtBinaryOperator)) {
            return;
        }
        CtBinaryOperator op = (CtBinaryOperator)candidate;
        op.setKind(BinaryOperatorKind.PLUS);
    }
}