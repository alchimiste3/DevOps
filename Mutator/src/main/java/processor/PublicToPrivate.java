package processor;

import spoon.reflect.declaration.*;

/**
 * Created by sualty on 10/03/16.
 */
public class PublicToPrivate extends AbstractProjectProcessor {
    public PublicToPrivate() {
        super("PublicToPrivate");
    }


    @Override
    boolean pecularVerify(CtElement candidate) {
        if(candidate instanceof CtMethod) {
            ModifierKind k = ((CtMethod) candidate).getVisibility();
            if (k == ModifierKind.PUBLIC){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isToBeProcessed(CtElement candidate) {
        CtPackage p = candidate.getParent(CtPackage.class);
        CtClass c = candidate.getParent(CtClass.class);
        try {
            if(pecularVerify(candidate)){
                if(verifyPackage(p.getSimpleName())){
                    if(verifyClass(c.getSimpleName())){
                            return verifyNbApplication();
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
        ((CtMethod)candidate).setVisibility(ModifierKind.PRIVATE);
    }
}