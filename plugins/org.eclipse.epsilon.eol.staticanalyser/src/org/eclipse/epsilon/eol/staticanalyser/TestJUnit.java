package org.eclipse.epsilon.eol.staticanalyser;

import java.io.File;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.common.parse.Region;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.ExpressionStatement;
import org.eclipse.epsilon.eol.dom.Statement;
import org.junit.Test;

import junit.framework.TestCase;

public class TestJUnit extends TestCase{

	@Test
	public static void test() throws Exception {
		
		EolModule module = new EolModule();
		module.parse(new File("src/org/eclipse/epsilon/eol/staticanalyser/TestCaseOperationsContextType.eol"));
		module.getCompilationContext().setModelFactory(new NewModelFactory());
		
		EolStaticAnalyser staticanalyser = new EolStaticAnalyser();
		staticanalyser.validate(module);
		
		List<Statement> statements = module.getMain().getStatements();

		int index = 0;

		for (Statement statement : statements) {
			String expected = statement.getComments().toString();
			expected = expected.substring(1, expected.length() - 1);
			
			int commentLine = statement.getRegion().getStart().getLine();
			
			int markerLine = staticanalyser.getErrors().get(index).getRegion()
					.getStart().getLine();
			
			String actual = staticanalyser.getErrors().get(index).getMessage();
				System.out.println("Expected # " + commentLine + ": " + expected);
				System.out.println("Actual # " + markerLine + ": " + actual);
				
				if(commentLine==markerLine) {
					System.out.println(expected.equalsIgnoreCase(actual));
					assertEquals("Failed", expected, actual);
					index++;
				}

			
		}
	}

}
