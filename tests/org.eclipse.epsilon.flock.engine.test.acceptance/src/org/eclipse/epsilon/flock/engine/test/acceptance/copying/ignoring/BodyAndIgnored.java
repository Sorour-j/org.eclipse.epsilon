/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.engine.test.acceptance.copying.ignoring;

import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class BodyAndIgnored extends FlockAcceptanceTest {
	
	private static final String strategy = "migrate Family ignoring numberOfChildren {" +
	                                       "  migrated.name = original.name.replace('The ', '');" + 
	                                       "}";

	private static final String originalModel = "Families {"                  +
	                                            "	Family {"                 +
	                                            "		name: \"The Smiths\"" +
	                                            "       members: Person \"p\" { name: \"John\" } " +
	                                            "		numberOfChildren: 1"  +
	                                            "	}"                        +
	                                            "}";

	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, originalModel);

		migrated.setVariable("family", "Family.all.first");
	}


	@Test
	public void numberOfChildrenIsIgnored() {
		migrated.assertEquals(0, "family.numberOfChildren");
	}

	@Test
	public void nameIsMigratedAccordingToRule() {
		migrated.assertEquals("Smiths", "family.name");
	}
	
	@Test
	public void membersIsCopied() {
		migrated.assertEquals(1,      "family.members.size");
		migrated.assertEquals("John", "family.members.first.name");
	}
}
