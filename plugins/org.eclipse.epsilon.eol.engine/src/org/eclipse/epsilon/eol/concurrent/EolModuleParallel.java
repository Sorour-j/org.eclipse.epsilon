/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.concurrent;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.concurrent.EolContextParallel;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EolModuleParallel extends EolModule {

	protected static final Set<String> CONFIG_PROPERTIES = new HashSet<>(2);
	static {
		CONFIG_PROPERTIES.add(IEolContextParallel.NUM_THREADS_CONFIG);
	}
	
	public EolModuleParallel() {
		setContext(new EolContextParallel());
	}
	
	public EolModuleParallel(int numThreads) {
		setContext(new EolContextParallel(numThreads));
	}
	
	@Override
	protected void prepareContext() {
		super.prepareContext();
		getContext().goParallel();
	}
	
	@Override
	public Object executeImpl() throws EolRuntimeException {
		Object result = super.executeImpl();
		getContext().endParallel();
		return result;
	}
	
	@Override
	public void setContext(IEolContext context) {
		if (context instanceof IEolContextParallel) {
			super.setContext(context);
		}
	}
	
	@Override
	public IEolContextParallel getContext() {
		return (IEolContextParallel) super.getContext();
	}
	
	/**
	 * WARNING: This method should only be called by the DT plugin for initialization purposes,
	 * as the context will be reset!
	 */
	@Override
	public void configure(Map<String, ?> properties) throws IllegalArgumentException {
		super.configure(properties);
		setContext(IEolContextParallel.configureContext(properties, EolContextParallel::new, getContext()));
	}
}
