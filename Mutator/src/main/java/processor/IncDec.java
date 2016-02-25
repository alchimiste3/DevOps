package processor;

import spoon.processing.AbstractProcessor;
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