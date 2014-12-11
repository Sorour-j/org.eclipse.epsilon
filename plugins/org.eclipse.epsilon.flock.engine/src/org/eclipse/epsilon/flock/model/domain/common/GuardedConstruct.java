/*******************************************************************************
 * Copyright (c) 2013 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.model.domain.common;

import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.execution.GuardedConstructContext;
import org.eclipse.epsilon.flock.parse.FlockParser;

public abstract class GuardedConstruct extends FlockConstruct {

	private ExecutableBlock<Boolean> guard;
	
	@SuppressWarnings("unchecked")
	@Override
	public void build() {
		super.build();
		this.guard = (ExecutableBlock<Boolean>)this.getFirstChildWithType(FlockParser.GUARD);
	}
	
	protected ExecutableBlock<Boolean> getGuard() {
		return guard;
	}
	
	public boolean appliesIn(GuardedConstructContext context) throws EolRuntimeException {
		return context.satisfies(guard);
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof GuardedConstruct))
			return false;
		
		final GuardedConstruct other = (GuardedConstruct)object;
		
		return super.equals(other) &&
		       guard.equals(other.guard);
	}
	
	@Override
	public int hashCode() {
		return guard == null ? 0 : guard.hashCode();
	}
}
