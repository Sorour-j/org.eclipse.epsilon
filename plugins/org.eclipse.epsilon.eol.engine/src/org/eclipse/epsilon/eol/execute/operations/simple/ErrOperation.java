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
package org.eclipse.epsilon.eol.execute.operations.simple;

import java.io.PrintStream;

import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class ErrOperation extends AbstractPrintOperation {

	@Override
	public PrintStream getPrintStream(IEolContext context) {
		return context.getErrorStream();
	}

	@Override
	public boolean printsNewLine() {
		return false;
	}

}
