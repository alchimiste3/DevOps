
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import spoon.Launcher;
import spoon.processing.Processor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.TypeFilter;

/** mutates and kills mutants of type T.
 * 
 *  @See {@link MutationTesterTest} for an example usage
 */
public class MutationTester {

	private String sourceCodeToBeMutated;
	private Processor mutator;
	
	/** the produced mutants */
	private final List<CtClass> mutants = new ArrayList<CtClass>();

	public MutationTester(String src, Processor mutator) {
		this.sourceCodeToBeMutated = src;
		this.mutator = mutator;
	}

	/** returns a list of mutant classes */
	public void generateMutants(String src) {
		Launcher l = new Launcher();
		l.addInputResource(sourceCodeToBeMutated);
		l.buildModel();
        l.setSourceOutputDirectory(src+"/mute");

		CtClass origClass = (CtClass) l.getFactory().Package().getRootPackage().getElements(new TypeFilter(CtClass.class)).get(0);

        Iterator<CtPackage> it = l.getFactory().Package().getAll().iterator();
//        it.next();
//	    CtClass origClass = (CtClass) it.next().getElements(new TypeFilter(CtClass.class)).get(0);
        
        System.out.println(l.getFactory().Package().getRootPackage().getPackages().iterator().next().getPackages().iterator().next());
        
		// now we apply a transformation
		// we replace "+" and "*" by "-"
		List<CtElement> elementsToBeMutated = origClass.getElements(new Filter<CtElement>() {

			public boolean matches(CtElement arg0) {
				return mutator.isToBeProcessed(arg0);
			}
		});
		
		for (CtElement e : elementsToBeMutated) {
			// this loop is the trickiest part
			// because we want one mutation after the other
			
			// cloning the AST element
			CtElement op = l.getFactory().Core().clone(e);
			
			// mutate the element
			mutator.process(op);
			
			// temporarily replacing the original AST node with the mutated element 
			replace(e,op);
		}
                l.prettyprint();
	}

	public List<CtClass> getMutants() {
		return Collections.unmodifiableList(mutants);
	}
	
	private void replace(CtElement e, CtElement op) {
		if (e instanceof CtStatement  && op instanceof CtStatement) {
			((CtStatement)e).replace((CtStatement) op);			
			return;
		}
		if (e instanceof CtExpression && op instanceof CtExpression) {
			((CtExpression)e).replace((CtExpression) op);
			return;
		}
		throw new IllegalArgumentException(e.getClass()+" "+op.getClass());
	}

}