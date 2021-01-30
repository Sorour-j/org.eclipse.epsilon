/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.TestUnit.Standalone;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.effectivemetamodel.SmartEMF;
import org.eclipse.epsilon.eol.launch.EolRunConfiguration;
import org.eclipse.epsilon.evl.launch.EvlRunConfiguration;
import org.eclipse.epsilon.emc.emf.EmfModel;

/**
 * This example demonstrates using the Epsilon Object Language, the core language of Epsilon, in a stand-alone manner 
 * 
 * @author Sina Madani
 * @author Dimitrios Kolovos
 */
public class EvlStandaloneExample_SmartEMF {
	
	public static void main(String... args) throws Exception {
		Path root = Paths.get(EvlStandaloneExample_SmartEMF.class.getResource("").toURI()),
			modelsRoot = root.getParent().resolve("standalone");
		
		StringProperties modelProperties = new StringProperties();
		modelProperties.setProperty(SmartEMF.PROPERTY_NAME, "javaMM");
		modelProperties.setProperty(SmartEMF.PROPERTY_FILE_BASED_METAMODEL_URI,
			modelsRoot.resolve("java.ecore").toAbsolutePath().toUri().toString()
			
		);
		modelProperties.setProperty(SmartEMF.PROPERTY_METAMODEL_URI,"http://www.eclipse.org/MoDisco/Java/0.2.incubation/java");
		modelProperties.setProperty("type", "SmartEMF");
		modelProperties.setProperty(SmartEMF.PROPERTY_MODEL_URI,
			modelsRoot.resolve("test.xmi").toAbsolutePath().toUri().toString()
		);
		
		EvlRunConfiguration runConfig = EvlRunConfiguration.Builder()
			.withScript(root.resolve("test.evl"))
			.withModel(new SmartEMF(), modelProperties)
			.withParameter("greeting", "Hello from ")
			.withProfiling()
			.withResults()
			.build();
		SmartEMFRunConfiguration sm = new SmartEMFRunConfiguration(runConfig);
		sm.run();
		runConfig.postExecute();
		//System.out.println(sm.getResult());
	}
}
