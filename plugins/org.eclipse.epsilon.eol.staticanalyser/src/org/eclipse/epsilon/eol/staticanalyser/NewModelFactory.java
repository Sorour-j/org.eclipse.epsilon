package org.eclipse.epsilon.eol.staticanalyser;

import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.compile.context.IModelFactory;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.tools.ModelFactory;

public class NewModelFactory implements IModelFactory{

	@Override
	public IModel createModel(String driver) {
		// TODO Auto-generated method stub
		EmfModel emf = new EmfModel();
		return emf;
	}

}
