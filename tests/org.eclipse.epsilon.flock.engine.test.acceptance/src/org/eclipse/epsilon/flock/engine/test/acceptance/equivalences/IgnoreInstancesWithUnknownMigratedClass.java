/*******************************************************************************
 * Copyright (c) 2009 The University of York.
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
package org.eclipse.epsilon.flock.engine.test.acceptance.equivalences;

import static org.eclipse.epsilon.test.util.builders.emf.MetamodelBuilder.aMetamodel;
import static org.junit.Assert.assertFalse;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;


public class IgnoreInstancesWithUnknownMigratedClass extends FlockAcceptanceTest {

	private static final String strategy = "";
	
	private static final String originalModel = "Families {"             +
	                                            "	Person {"            +
	                                            "		name: \"John\""  +
	                                            "	}"                   +
	                                            "}";
	
	private static final EPackage evolvedMetamodel = aMetamodel().build();
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesTo(evolvedMetamodel, strategy, originalModel);
	}
	
	@Test
	public void migratedModelShouldBeEmpty() {
		assertFalse(migrated.getResource().getAllContents().hasNext());
	}
}