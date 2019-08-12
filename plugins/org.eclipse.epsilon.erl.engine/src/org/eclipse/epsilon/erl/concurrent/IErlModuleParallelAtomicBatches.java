/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.concurrent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.Callable;
import java.util.stream.BaseStream;
import java.util.stream.StreamSupport;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.execute.context.concurrent.IErlContextParallel;
import org.eclipse.epsilon.erl.execute.data.JobBatch;
import org.eclipse.epsilon.erl.execute.data.RuleAtom;

/**
 * Convenience trait for rule-based languages which have deterministically
 * ordered jobs that can be split into "Atoms" and divided up into batches
 * (i.e. Lists of "Atoms") for parallel execution.
 *
 * @author Sina Madani
 * @since 1.6
 */
public interface IErlModuleParallelAtomicBatches<D extends RuleAtom<?>> extends IErlModuleParallel {

	/**
	 * The atomic units of work.
	 * 
	 * @return A deterministically ordered List of executable rule-element pairs.
	 * @throws EolRuntimeException If any problems are encountered when retrieving the jobs.
	 */
	List<? extends D> getAllJobs() throws EolRuntimeException;
	
	/**
	 * Evaluates the job locally.
	 * 
	 * @param job The job (or jobs) to evaluate.
	 * @param isInLoop Whether this method is being called recursively from a loop.
	 * @throws EolRuntimeException If an exception is thrown whilst evaluating the job(s).
	 * @return The result of evaluating the job.
	 */
	@SuppressWarnings("unchecked")
	default Object executeJobImpl(Object job, boolean isInLoop) throws EolRuntimeException {
		if (job instanceof JobBatch) {
			return executeJobImpl(((JobBatch) job).split(getAllJobs()), isInLoop);
		}
		else if (job instanceof RuleAtom) {
			return ((RuleAtom<?>) job).execute(getContext());
		}
		else if (job instanceof Iterable) {
			return executeJobImpl(((Iterable<?>) job).iterator(), isInLoop);
		}
		else if (job instanceof Iterator) {
			final List<Object> results;
			IErlContextParallel context = getContext();
			if (isInLoop) {
				results = new LinkedList<>();
				for (
					Iterator<?> iter = (Iterator<?>) job;
					iter.hasNext();
					results.add(executeJobImpl(iter.next(), isInLoop))
				);
				
				return results;
			}
			else {
				assert context.isParallelisationLegal();
				List<Callable<Object>> jobs = new LinkedList<>();
				
				for (Iterator<?> iter = (Iterator<?>) job; iter.hasNext();) {
					final Object nextJob = iter.next();
					
					jobs.add(nextJob instanceof Callable ?
						(Callable<Object>) nextJob :
						() -> executeJobImpl(nextJob, true)
					);
				}
				
				return context.executeParallelTyped(jobs);
			}
		}
		else if (job instanceof BaseStream) {
			return executeJobImpl(((BaseStream<?,?>)job).iterator(), isInLoop);
		}
		else if (job instanceof Spliterator) {
			return executeJobImpl(StreamSupport.stream(
				(Spliterator<?>) job, getContext().isParallelisationLegal()), isInLoop
			);
		}
		else {
			throw new IllegalArgumentException("Received unexpected object of type "+job.getClass().getName());
		}
	}
	
	/**
	 * Splits the job list (as returned from {@link #getAllJobs()} into the specified number of batches.
	 * @param batchSize This is interpreted as the size of batches, i.e. the distance between start and end indices.
	 * @return The list of start and end indices in order.
	 * @see {@link JobBatch#getBatches(int, int)}.
	 * @throws EolRuntimeException
	 */
	default List<JobBatch> getBatchJobs(int batchSize) throws EolRuntimeException {
		return JobBatch.getBatches(getAllJobs().size(), batchSize);
	}
}