/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.edl.dt.wizards;

import org.eclipse.epsilon.common.dt.wizards.AbstractNewFileWizard2;

public class NewEdlFileWizard extends AbstractNewFileWizard2 {

	@Override
	public String getTitle() {
		return "New EDL Demo";
	}

	@Override
	public String getExtension() {
		return "edl";
	}

	@Override
	public String getDescription() {
		return "This wizard creates a new EDL demo with *.edl extension.";
	}
	
}
