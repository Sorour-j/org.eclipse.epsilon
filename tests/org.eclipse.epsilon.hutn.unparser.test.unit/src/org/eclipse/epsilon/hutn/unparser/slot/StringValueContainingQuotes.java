/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.unparser.slot;

import org.eclipse.epsilon.hutn.unparser.internal.AbstractSlotUnparserTest;
import org.junit.BeforeClass;

public class StringValueContainingQuotes extends AbstractSlotUnparserTest {
	
	@BeforeClass
	public static void setup() {
		attributeSlotTest("name", new Object[] {"John \"Invisible\" Doe"}, new String[] {"\"John \\\"Invisible\\\" Doe\""});
	}
}
