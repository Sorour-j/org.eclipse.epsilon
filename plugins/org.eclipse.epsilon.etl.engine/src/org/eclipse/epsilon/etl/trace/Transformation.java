/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.trace;

import org.eclipse.epsilon.eol.types.EolCollection;
import org.eclipse.epsilon.etl.TransformRule;

public class Transformation {
	
	protected Object source;
	protected EolCollection targets;
	protected TransformRule rule;
	
	public Transformation(){
		
	}
	
	public Transformation(Object source, EolCollection targets) {
		super();
		this.source = source;
		this.targets = targets;
	}
	
	public Object getSource() {
		return source;
	}
	
	public void setSource(Object source) {
		this.source = source;
	}
	
	public EolCollection getTargets() {
		return targets;
	}

	public void setTargets(EolCollection targets) {
		this.targets = targets;
	}

	public boolean of(Object source) {
		return this.source == source;
	}

	public TransformRule getRule() {
		return rule;
	}

	public void setRule(TransformRule rule) {
		this.rule = rule;
	}
	
}
