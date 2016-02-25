package processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtElement;


public class GELE extends AbstractProcessor<CtElement> {

    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        return candidate instanceof CtBinaryOperator;
    }

    @Override
    public void process(CtElement candidate) {
        if (!(candidate instanceof CtBinaryOperator)) {
            return;
        }
        else if(((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.LE){
            CtBinaryOperator op = (CtBinaryOperator)candidate;
            op.setKind(BinaryOperatorKind.GE);
        }
        else if(((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.GE){
            CtBinaryOperator op = (CtBinaryOperator)candidate;
            op.setKind(BinaryOperatorKind.LE);
        }

    }
}