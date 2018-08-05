/*******************************************************************************
 * Copyright (c) 2008-2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - refactoring
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.declarative;

import java.util.Collection;
import java.util.function.Function;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;

public class ForAllOperation extends FirstOrderOperation {
	
	protected Function<Integer, ? extends NMatchOperation> delegateConstructor = NMatchOperation::new;
	
	@Override
	public Boolean execute(Object target, Variable iterator, Expression expression,
			IEolContext context) throws EolRuntimeException {
		
		Collection<Object> source = CollectionUtil.asCollection(target);
		return delegateConstructor.apply(source.size()).execute(target, iterator, expression, context);
	}

}
