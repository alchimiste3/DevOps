package processor;

import spoon.processing.AbstractProcessor;
import spoon.processing.ProcessingManager;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtElement;


public class IncDec extends AbstractProcessor<CtElement> {

    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        return candidate instanceof CtUnaryOperator;
    }

    @Override
    public void process(CtElement candidate) {
        if (!(candidate instanceof CtUnaryOperator && ((CtUnaryOperator)candidate).getKind()==UnaryOperatorKind.POSTINC)) {
            return;
        }
        CtUnaryOperator op = (CtUnaryOperator)candidate;
        op.setKind(UnaryOperatorKind.POSTDEC);
    }
}