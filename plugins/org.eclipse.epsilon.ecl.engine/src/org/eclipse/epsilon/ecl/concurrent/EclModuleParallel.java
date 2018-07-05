package org.eclipse.epsilon.ecl.concurrent;

import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.dom.MatchRule;
import org.eclipse.epsilon.ecl.execute.context.concurrent.EclContextParallel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.concurrent.executors.EolExecutorService;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EclModuleParallel extends EclModule {

	public EclModuleParallel() {
		this(0);
	}
	
	public EclModuleParallel(int parallelism) {
		this.context = new EclContextParallel(parallelism);
	}
	
	@Override
	protected void prepareExecution() throws EolRuntimeException {
		super.prepareExecution();
		getContext().goParallel();
	}
	
	@Override
	protected void postExecution() throws EolRuntimeException {
		getContext().endParallel();
		super.postExecution();
	}
	
	@Override
	protected void matchAllRules(boolean greedy) throws EolRuntimeException {
		boolean ofTypeOnly = !greedy;
		EclContextParallel context = getContext();
		EolExecutorService executor = context.newExecutorService();
		
		for (MatchRule matchRule : getMatchRules()) {
			if (!matchRule.isAbstract() && !matchRule.isLazy(context) && (ofTypeOnly || matchRule.isGreedy())) {
				for (Object left : matchRule.getLeftInstance(context, ofTypeOnly)) {
					for (Object right : matchRule.getRightInstance(context, ofTypeOnly)) {
						executor.execute(() -> {
							try {
								matchRule.matchAll(context, ofTypeOnly, left, right);
							}
							catch (EolRuntimeException ex) {
								context.handleException(ex, executor);
							}
						});
					}
				}
			}
		}
		executor.awaitCompletion();
	}

	@Override
	public EclContextParallel getContext() {
		return (EclContextParallel) context;
	}
	
	@Override
	public void setContext(IEolContext context) {
		if (context instanceof EclContextParallel) {
			super.setContext(context);
		}
	}
}