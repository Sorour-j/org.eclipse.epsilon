package org.eclipse.epsilon.eol.execute.operations.declarative.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.concurrent.executors.EolExecutorService;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.context.concurrent.EolContextParallel;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;
import org.eclipse.epsilon.eol.execute.operations.declarative.NMatchOperation;
import org.eclipse.epsilon.eol.types.EolType;

public class ParallelNMatchOperation extends NMatchOperation {
	
	public ParallelNMatchOperation(int targetMatches) {
		super(targetMatches);
	}
	
	@Override
	public Boolean execute(Object target, Variable iterator, Expression expression,
			IEolContext context_) throws EolRuntimeException {
		
		Collection<Object> source = CollectionUtil.asCollection(target);
		if (source.size() < targetMatches) return false;

		EolType iteratorType = iterator.getType();
		String iteratorName = iterator.getName();
		IEolContextParallel context = EolContextParallel.convertToParallel(context_);

		AtomicInteger currentMatches = new AtomicInteger(), index = new AtomicInteger();
		Collection<Future<?>> jobs = new ArrayList<>(source.size());
		
		EolExecutorService executor = context.beginParallelJob(expression);
		
		for (Object item : source) {
			final int currentIndex = index.incrementAndGet();
			
			if (iteratorType == null || iteratorType.isKind(item)) {
				jobs.add(executor.submit(() -> {
					
					FrameStack scope = context.getFrameStack();
					scope.enterLocal(FrameType.UNPROTECTED, expression,
						new Variable(iteratorName, item, iteratorType, true)
					);
					
					Object bodyResult = null;
					try {
						bodyResult = context.getExecutorFactory().execute(expression, context);
					}
					catch (EolRuntimeException ex) {
						context.handleException(ex, executor);
					}
					
					if (bodyResult instanceof Boolean && (boolean) bodyResult) { 
						int currentMatchesCached = currentMatches.incrementAndGet();
						if (
							currentMatchesCached > targetMatches ||
							currentIndex > targetMatches && (currentMatchesCached < targetMatches)
						) {
							scope.leaveLocal(expression);
							executor.getExecutionStatus().completeSuccessfully();
							return;
						}
					}
					
					scope.leaveLocal(expression);
				}));
			}
		}
		
		// Prevent unnecessary evaluation of remaining jobs once we have the result
		executor.shortCircuitCompletion(jobs);
		context.endParallelJob(executor, expression);
		
		return currentMatches.get() == targetMatches;
	}
}
