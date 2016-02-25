package processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtElement;

public class PlusMinus extends AbstractProcessor<CtElement> {
	@Override
	public boolean isToBeProcessed(CtElement candidate) {
		return candidate instanceof CtBinaryOperator;
	}

	@Override
	public void process(CtElement candidate) {
		if (!(candidate instanceof CtBinaryOperator)) {
			return;
		}
        else if (((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.PLUS) {
            CtBinaryOperator op = (CtBinaryOperator)candidate;
            op.setKind(BinaryOperatorKind.MINUS);
        }
        else if (((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.MINUS) {
            CtBinaryOperator op = (CtBinaryOperator)candidate;
            op.setKind(BinaryOperatorKind.PLUS);
        }

	}
/*
    @Override
    public void processingDone() {
        ProcessingManager p = this.getEnvironment().getManager();
        p.addProcessor(IncDec.class);
    }*/
}