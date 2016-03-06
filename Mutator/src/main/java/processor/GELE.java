package processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;


public class GELE extends AbstractProcessor<CtElement> {

    private String nameOfClass="Classe1";
    private int nb_of_applications=3;

    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        int nb_found=0;
        if(nb_found<nb_of_applications) {
            if(candidate instanceof CtBinaryOperator) {
                if(candidate.getParent(CtClass.class).getSimpleName().equals(nameOfClass)) {
                    if(((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.LE
                        ||((CtBinaryOperator)candidate).getKind()==BinaryOperatorKind.GE) {
                        nb_found++;
                        return true;
                    }
                }
            }
        }
        return false;
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