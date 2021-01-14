package org.eclipse.epsilon.TestUnit.Parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import junit.framework.TestCase;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.epsilon.SmartSaxParser.Demonstration;
import org.eclipse.epsilon.common.parse.Region;
import org.eclipse.epsilon.effectivemetamodel.EffectiveFeature;
import org.eclipse.epsilon.effectivemetamodel.SmartEMF;
import org.eclipse.epsilon.effectivemetamodel.EffectiveMetamodelExtraction;
import org.eclipse.epsilon.effectivemetamodel.EffectiveType;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.ExpressionStatement;
import org.eclipse.epsilon.eol.dom.Statement;
import org.eclipse.epsilon.eol.parse.Eol_EolParserRules.returnStatement_return;
import org.junit.Test;

public class TestJUnitofEolEffectiveMetamodel{

	String metamodel = "src/org/eclipse/epsilon/TestUnit/Parser/flowchart.ecore";
	String model = "src/org/eclipse/epsilon/TestUnit/Parser/flowchart2.xmi";
	EList<EObject> actualList;
	EList<EObject> expectedList;
	
	@Test
	public void allInstances() throws Exception {
		
		String eolFile = "src/org/eclipse/epsilon/TestUnit/Parser/AllInstancesTest.eol";
		SmartEMF actualEf,expectedEf;
		
		/*Actual*/
		EffectiveMetamodelExtraction ef = new EffectiveMetamodelExtraction(metamodel, eolFile);
		actualEf = ef.getEffectiveMetamodel();
		
		/*Expected*/
		expectedEf = new SmartEMF();
		expectedEf.setName("flowchart");
		expectedEf.addToAllOfKind("Node");
		expectedEf.addAttributeToAllOfKind("Node", "name");
		
		//change the  message*////
		assertEquals("Failed", compare (actualEf , expectedEf), true);
		
		Demonstration demo = new Demonstration(model); 
		demo.setEfMetamodel(actualEf);
		actualList = demo.demo();
		
		boolean ok =true;
		int act=0,dec=0;
		if (actualList.size() == 4)
		{
			for (EObject obj : actualList) {
			
				
				if (obj.eClass().getName().equals("Action"))
					act++;
				else if (obj.eClass().getName().equals("Decision"))
					dec++;
				else
					ok=false;
			}
			if (act != 2 || dec!=2)
				ok=false;
		}
		else 
			ok = false;
		
		assertEquals("Failed", ok, true);
		
	}
	
	@Test
	public void AllofTypeforSubClass() throws Exception {
		
		String eolFile = "src/org/eclipse/epsilon/TestUnit/Parser/AllofTypeforSubClassTest.eol";
		SmartEMF actualEf,expectedEf;
		
		/*Actual*/
		EffectiveMetamodelExtraction ef = new EffectiveMetamodelExtraction(metamodel, eolFile);
		actualEf = ef.getEffectiveMetamodel();
		
		/*Expected*/
		expectedEf = new SmartEMF();
		expectedEf.setName("flowchart");
		expectedEf.addToAllOfType("Action");
		expectedEf.addAttributeToAllOfType("Action", "name");
		
		assertEquals("Failed", compare (actualEf , expectedEf), true);
			
		}
	
	@Test
	public void AllofTypeforSuperClass() throws Exception {
		
		String eolFile = "src/org/eclipse/epsilon/TestUnit/Parser/AllofTypeforSuperClassTest.eol";
		SmartEMF actualEf,expectedEf;
		
		/*Actual*/
		EffectiveMetamodelExtraction ef = new EffectiveMetamodelExtraction(metamodel, eolFile);
		actualEf = ef.getEffectiveMetamodel();
		
		/*Expected*/
		expectedEf = new SmartEMF();
		expectedEf.setName("flowchart");
		
		assertEquals("Failed", compare (actualEf , expectedEf), true);
			
		}
	
	@Test
	public void CollectionofModelElement() throws Exception {
		
		String eolFile = "src/org/eclipse/epsilon/TestUnit/Parser/CollectionofModelElementTest.eol";
		SmartEMF actualEf,expectedEf;
		
		/*Actual*/
		EffectiveMetamodelExtraction ef = new EffectiveMetamodelExtraction(metamodel, eolFile);
		actualEf = ef.getEffectiveMetamodel();
		
		/*Expected*/
		expectedEf = new SmartEMF();
		expectedEf.setName("flowchart");
		expectedEf.addToAllOfType("Action");
		expectedEf.addAttributeToAllOfType("Action", "name");
		expectedEf.addToTypes("Transition");
		expectedEf.addAttributeToTypes("Transition", "name");
		
		assertEquals("Failed", compare (actualEf , expectedEf), true);
		}
	
	@Test
	public void NonContainmentRefrence() throws Exception {
		
		String eolFile = "src/org/eclipse/epsilon/TestUnit/Parser/NonContainmentRefrenceTest.eol";
		SmartEMF actualEf,expectedEf;
		
		/*Actual*/
		EffectiveMetamodelExtraction ef = new EffectiveMetamodelExtraction(metamodel, eolFile);
		actualEf = ef.getEffectiveMetamodel();
		
		/*Expected*/
		expectedEf = new SmartEMF();
		expectedEf.setName("flowchart");
		expectedEf.addToAllOfKind("Node");
		expectedEf.addToTypes("Transition");
		expectedEf.addReferenceToAllOfKind("Node", "incoming");
		
		assertEquals("Failed", compare (actualEf , expectedEf), true);
	
		}
	
	@Test
	public void subClass() throws Exception {
		
		String eolFile = "src/org/eclipse/epsilon/TestUnit/Parser/SubClassTest.eol";
		SmartEMF actualEf,expectedEf;
		
		/*Actual*/
		EffectiveMetamodelExtraction ef = new EffectiveMetamodelExtraction(metamodel, eolFile);
		actualEf = ef.getEffectiveMetamodel();
		
		/*Expected*/
		expectedEf = new SmartEMF();
		expectedEf.setName("flowchart");
		expectedEf.addToAllOfKind("Action");
		expectedEf.addAttributeToAllOfKind("Action", "name");
		
		assertEquals("Failed", compare (actualEf , expectedEf), true);
	
		}
	
	@Test
	public void superClass() throws Exception {
		
		String eolFile = "src/org/eclipse/epsilon/TestUnit/Parser/SuperClassTest.eol";
		SmartEMF actualEf,expectedEf;
		
		/*Actual*/
		EffectiveMetamodelExtraction ef = new EffectiveMetamodelExtraction(metamodel, eolFile);
		actualEf = ef.getEffectiveMetamodel();
		
		/*Expected*/
		expectedEf = new SmartEMF();
		expectedEf.setName("flowchart");
		expectedEf.addToAllOfKind("Node");
		expectedEf.addAttributeToAllOfKind("Node", "name");
		
		assertEquals("Failed", compare (actualEf , expectedEf), true);
	
		}
	@Test
	public void contaimentRefrence() throws Exception {
		
		metamodel = "src/org/eclipse/epsilon/TestUnit/Parser/componentLanguage.ecore";
		String eolFile = "src/org/eclipse/epsilon/TestUnit/Parser/ContainmentRefrenceTest.eol";
		
		SmartEMF actualEf,expectedEf;
		
		/*Actual*/
		EffectiveMetamodelExtraction ef = new EffectiveMetamodelExtraction(metamodel, eolFile);
		actualEf = ef.getEffectiveMetamodel();
		
		/*Expected*/
		expectedEf = new SmartEMF();
		expectedEf.setName("componentLanguage");
		expectedEf.addToAllOfKind("Component");
		expectedEf.addReferenceToAllOfKind("Component","ports");
		expectedEf.addToTypes("Port");
		
		assertEquals("Failed", compare (actualEf , expectedEf), true);
	
		}
	@Test
	public void EnumerationType() throws Exception {
		
		metamodel = "src/org/eclipse/epsilon/TestUnit/Parser/componentLanguage.ecore";
		String eolFile = "src/org/eclipse/epsilon/TestUnit/Parser/EnumerationTypeTest.eol";
		
		SmartEMF actualEf,expectedEf;
		
		/*Actual*/
		EffectiveMetamodelExtraction ef = new EffectiveMetamodelExtraction(metamodel, eolFile);
		actualEf = ef.getEffectiveMetamodel();
		
		/*Expected*/
		expectedEf = new SmartEMF();
		expectedEf.setName("componentLanguage");
		expectedEf.addToAllOfKind("Component");
		expectedEf.addReferenceToAllOfKind("Component","ports");
		expectedEf.addToTypes("Port");
		expectedEf.addAttributeToTypes("Port", "type");
		
		assertEquals("Failed", compare (actualEf , expectedEf), true);
	
		}
	
	
	public static boolean compare(SmartEMF actual, SmartEMF expected) {
		
		int i = 0;
		int j = 0;
		EffectiveType actualtype;
		EffectiveFeature actualfeature;

		//Check AllofKind
		for (EffectiveType expectedtype : expected.getAllOfKind()) {

			actualtype = actual.getAllOfKind().get(i);

			if (actual.getAllOfKind().get(i).isequals(expectedtype)) {

				actualtype = actual.getAllOfKind().get(i);

				for (EffectiveFeature expectedfeature : expectedtype.getAllFeatures()) {
					actualfeature = actualtype.getAllFeatures().get(j);

					if (!actualfeature.equals(expectedfeature))
						return false;
					else
						j++;
				}
				i++;

			} else
				return false;

		}
		
		//Check AllofType
		for (EffectiveType expectedtype : expected.getAllOfType()) {

			i = 0;j = 0;
			actualtype = actual.getAllOfType().get(i);

			if (actual.getAllOfType().get(i).isequals(expectedtype)) {

				actualtype = actual.getAllOfType().get(i);

				for (EffectiveFeature expectedfeature : expectedtype.getAllFeatures()) {
					actualfeature = actualtype.getAllFeatures().get(j);

					if (!actualfeature.equals(expectedfeature))
						return false;
					else
						j++;
				}
				i++;

			} else
				return false;

		}
		//Check Types
		for (EffectiveType expectedtype : expected.getTypes()) {

			i = 0;j = 0;
			
			actualtype = actual.getTypes().get(i);

			if (actual.getTypes().get(i).isequals(expectedtype)) {

				actualtype = actual.getTypes().get(i);

				for (EffectiveFeature expectedfeature : expectedtype.getAllFeatures()) {
					actualfeature = actualtype.getAllFeatures().get(j);

					if (!actualfeature.equals(expectedfeature))
						return false;
					else
						j++;
				}
				i++;

			} else
				return false;

		}
		
		return true;
	}
}



