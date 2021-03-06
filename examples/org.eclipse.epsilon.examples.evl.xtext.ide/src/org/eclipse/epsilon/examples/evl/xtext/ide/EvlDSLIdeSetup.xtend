/*
 * generated by Xtext 2.14.0
 */
package org.eclipse.epsilon.examples.evl.xtext.ide

import com.google.inject.Guice
import org.eclipse.epsilon.examples.evl.xtext.EvlDSLRuntimeModule
import org.eclipse.epsilon.examples.evl.xtext.EvlDSLStandaloneSetup
import org.eclipse.xtext.util.Modules2

/**
 * Initialization support for running Xtext languages as language servers.
 */
class EvlDSLIdeSetup extends EvlDSLStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new EvlDSLRuntimeModule, new EvlDSLIdeModule))
	}
	
}
