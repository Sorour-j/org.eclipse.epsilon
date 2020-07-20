package org.eclipse.epsilon.effectivemetamodel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.compile.context.IModelFactory;
import org.eclipse.epsilon.eol.compile.m3.Attribute;
import org.eclipse.epsilon.eol.compile.m3.MetaClass;
import org.eclipse.epsilon.eol.compile.m3.Reference;
import org.eclipse.epsilon.eol.compile.m3.StructuralFeature;
import org.eclipse.epsilon.eol.dom.ModelDeclaration;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.eol.types.EolModelElementType;

public class EffectiveMetamodelExtraction {
	
//	public static void main(String[] args) {
		// TODO Auto-generated method stub
	protected String file;
	protected String path = null;//"src/org/eclipse/epsilon/TestUnit/Parser/flowchart.ecore";
	
	public EffectiveMetamodelExtraction(String path, String file) {
		this.path = path;
		this.file = file;
	}
	public EffectiveMetamodelExtraction() {}
	
public EffectiveMetamodel getEffectiveMetamodel () {
	
		//when we have more than one metamodel in one file
		//ArrayList<EffectiveMetamodel> effectiveMetamodels = new ArrayList<EffectiveMetamodel>();
		EffectiveMetamodel effectiveMetamodel = null;
		ArrayList<ModuleElement> children = new ArrayList<ModuleElement>();
		ArrayList<StructuralFeature> features = new ArrayList<StructuralFeature>();
		EffectiveType effectiveType;
		EolModelElementType target;
		EolModelElementType property;
		
		EolModule module = new EolModule();
		ResourceSet resourceSet = new ResourceSetImpl();
		
		ResourceSet ecoreResourceSet = new ResourceSetImpl();
		ecoreResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource ecoreResource = ecoreResourceSet.
				createResource(URI.createFileURI(new File(path).getAbsolutePath()));
		try {
			ecoreResource.load(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (EObject o : ecoreResource.getContents()) {
			EPackage ePackage = (EPackage) o;
			resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
			EPackage.Registry.INSTANCE.put(ePackage.getNsURI(), ePackage);
		}
		System.out.println(EPackage.Registry.INSTANCE.getEPackage(((EPackage)ecoreResource.getContents().get(0)).getNsURI()));
		
		try {
			
			module.parse(new File(file));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		module.getCompilationContext().setModelFactory(new SubModelFactory());
		module.compile();
	//	public ArrayList<EffectiveMetamodel> EffectiveMetamodelExtraction( EolModule module) {
	
			for (ModelDeclaration MD : module.getDeclaredModelDeclarations()) {
				//How can I add the nsuri?
				EffectiveMetamodel m = new EffectiveMetamodel(MD.getNameExpression().getName());
				m.setPath(path);
				//effectiveMetamodels.add(m);
				effectiveMetamodel = m;
			}

			children.addAll(module.getChildren());

			while (!(children.isEmpty())) {

				ModuleElement MD = children.get(0);
				children.remove(MD);
				// Improve : Use Optimized EolModule
				if ((!(MD.getChildren().isEmpty())) && !(MD instanceof Operation))
					children.addAll(MD.getChildren());

				if (MD instanceof OperationCallExpression) {

					OperationCallExpression operationCall = (OperationCallExpression) MD;
					
					for (Operation op : operationCall.getOperations())
						children.addAll(op.getChildren());
					
					if (!operationCall.isContextless()) {

						if (operationCall.getTargetExpression().getResolvedType() instanceof EolModelElementType) {

							operationCall.getTargetExpression().compile(module.getCompilationContext());
							target = (EolModelElementType) operationCall.getTargetExpression().getResolvedType();
							//elementName = target.getName();

						/*	for (EffectiveMetamodel Ef : effectiveMetamodels) {
								if (target.getModelName().equals("")) {
									effectiveMetamodel = effectiveMetamodels.get(0);
									break;
								}
								else 
									if (Ef.getName().equals(target.getModelName())) {
										effectiveMetamodel = Ef;
										break;
									}
							}*/
							
							if (operationCall.getNameExpression().getName().equals("all")
									|| operationCall.getNameExpression().getName().equals("allOfKind")
									|| operationCall.getNameExpression().getName().equals("allInstances")) {

								effectiveMetamodel.addToAllOfKind(target.getTypeName());

								if (effectiveMetamodel.allOfTypeContains(target.getTypeName())) {
									effectiveMetamodel.getAllOfType().remove(target.getTypeName());
								}

							} else if (operationCall.getNameExpression().getName().equals("allOfType")) {
								if (!effectiveMetamodel.allOfKindContains(target.getTypeName())
										&& !effectiveMetamodel.allOfTypeContains(target.getTypeName()))

									effectiveMetamodel.addToAllOfType(target.getTypeName());
							}
						}
					}
				}
				if (MD instanceof PropertyCallExpression) {

					PropertyCallExpression pro = (PropertyCallExpression) MD;
					pro.getTargetExpression().compile(module.getCompilationContext());
					pro.compile(module.getCompilationContext());


					if (pro.getTargetExpression().getResolvedType() instanceof EolModelElementType) {

						target = (EolModelElementType) pro.getTargetExpression().getResolvedType();

//						for (EffectiveMetamodel Ef : effectiveMetamodels) {
//							if (target.getModelName().equals("")) {
//								effectiveMetamodel = effectiveMetamodels.get(0);
//								break;
//							}
//							else 
//								if (Ef.getName().equals(target.getModelName())) {
//									effectiveMetamodel = Ef;
//									break;
//								}
//						}
						if (pro.getNameExpression().getName().equals("all")) {
							// Like AllofKind Algorithm
							effectiveMetamodel.addToAllOfKind(target.getTypeName());
							if (effectiveMetamodel.allOfTypeContains(target.getTypeName())) {
								effectiveMetamodel.getAllOfType().remove(target.getTypeName());
							}
						}
						// not already under the EM's types, allOfKind or allOfKind references
						else {
							effectiveType = new EffectiveType(target.getTypeName());
							effectiveType.setEffectiveMetamodel(effectiveMetamodel);

							if (effectiveMetamodel.allOfKindContains(effectiveType.getName()))
								effectiveType = effectiveMetamodel.getFromAllOfKind(effectiveType.getName());
							else if (effectiveMetamodel.allOfTypeContains(effectiveType.getName()))
								effectiveType = effectiveMetamodel.getFromAllOfType(effectiveType.getName());
							else
								// add target.getTypeName() under EM's types reference;
								effectiveType = effectiveMetamodel.addToTypes(effectiveType.getName());

							if (!target.getMetaClass().getAllStructuralFeatures().isEmpty()) {
								features.addAll(target.getMetaClass().getAllStructuralFeatures());
							}

							for (MetaClass metaclass : target.getMetaClass().getSuperTypes()) {
								features.addAll(metaclass.getAllStructuralFeatures());
							}

							for (StructuralFeature sf : features) {
								if (sf instanceof Attribute
										&& sf.getName().equals(pro.getNameExpression().getName())) {
									effectiveType.addToAttributes(sf.getName());
									break;
								} else if (sf instanceof Reference
										&& sf.getName().equals(pro.getNameExpression().getName())) {
									effectiveType.addToReferences(sf.getName());
									break;
								}
							}
						}
					} else if (pro.getTargetExpression().getResolvedType() instanceof EolCollectionType) {

						if (((EolCollectionType) pro.getTargetExpression().getResolvedType())
								.getContentType() instanceof EolModelElementType) {

							target = (EolModelElementType) ((EolCollectionType) pro.getTargetExpression().getResolvedType())
									.getContentType();

							effectiveType = new EffectiveType(target.getTypeName());

							if (effectiveMetamodel.allOfKindContains(effectiveType.getName()))
								effectiveType = effectiveMetamodel.getFromAllOfKind(effectiveType.getName());
							else if (effectiveMetamodel.allOfTypeContains(effectiveType.getName()))
								effectiveType = effectiveMetamodel.getFromAllOfType(effectiveType.getName());
							else
								// add elementName under EM's types reference;
								effectiveType = effectiveMetamodel.addToTypes(effectiveType.getName());

							if (!target.getMetaClass().getAllStructuralFeatures().isEmpty()) {
								features.addAll(target.getMetaClass().getAllStructuralFeatures());
							}
							for (MetaClass metaclass : target.getMetaClass().getSuperTypes()) {
								features.addAll(metaclass.getAllStructuralFeatures());

							}

							for (StructuralFeature sf : features) {
								if (sf instanceof Attribute
										&& sf.getName().equals(pro.getNameExpression().getName())) {
									effectiveType.addToAttributes(sf.getName());
									break;
								} else if (sf instanceof Reference
										&& sf.getName().equals(pro.getNameExpression().getName())) {
									effectiveType.addToReferences(sf.getName());
									break;
								}
							}
						}

					}
				}

			}

			
			
			return effectiveMetamodel;
//			for (EffectiveMetamodel Ef : effectiveMetamodels) {
//			
//				if (Ef.getAllOfKind().isEmpty() && Ef.getAllOfType().isEmpty() && Ef.getTypes().isEmpty())
//					{
//						effectiveMetamodels.remove(Ef);
//						break;
//					}
//				
//			}
//			
		//	return effectiveMetamodels;
		}

}
