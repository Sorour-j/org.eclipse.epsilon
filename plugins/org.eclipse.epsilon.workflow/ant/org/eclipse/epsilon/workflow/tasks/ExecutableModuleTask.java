/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.eol.models.ModelReference;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.models.ReflectiveModelReference;
import org.eclipse.epsilon.eol.tools.EolSystem;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.eclipse.epsilon.profiling.Profiler;
import org.eclipse.epsilon.profiling.ProfilingExecutionListener;
import org.eclipse.epsilon.workflow.tasks.hosts.HostManager;
import org.eclipse.epsilon.workflow.tasks.nestedelements.ModelNestedElement;
import org.eclipse.epsilon.workflow.tasks.nestedelements.ParameterNestedElement;
import org.eclipse.epsilon.workflow.tasks.nestedelements.VariableNestedElement;

public abstract class ExecutableModuleTask extends EpsilonTask {
	
	/** 
	 * Allow Epsilon Tasks to have arbitrary nested property settings
	 * @author Horacio Hoyos Rodriguez
	 *
	 */
	public class ModuleProperty {
		String name;
		String value;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	protected List<ModelNestedElement> modelNestedElements = new ArrayList<ModelNestedElement>();
	protected List<VariableNestedElement> usesVariableNestedElements = new ArrayList<VariableNestedElement>();
	protected List<VariableNestedElement> exportsVariableNestedElements = new ArrayList<VariableNestedElement>();
	protected List<ParameterNestedElement> parameterNestedElements = new ArrayList<ParameterNestedElement>();
	protected File src;
	protected String code = "";
	protected IEolModule module;
	protected boolean assertions = true;
	protected String uri;
	protected Object result;
	private boolean isGUI = true, isDebug = false;
	protected boolean setBeans = false;
	protected boolean fine;
	
	/**
	 * Provide a specific module implementation at runtime
	 */
	protected Class<? extends IEolModule> moduleImplementation;
	
	/** Task specific settings */
	List<ModuleProperty> properties = new ArrayList<>();
	
	static {
		HostManager.getHost().initialise();
	}

	public ModelNestedElement createModel() {
		ModelNestedElement model = new ModelNestedElement();
		modelNestedElements.add(model);
		return model;
	}
	
	public VariableNestedElement createUses() {
		VariableNestedElement variableNestedElement = new VariableNestedElement();
		usesVariableNestedElements.add(variableNestedElement);
		return variableNestedElement;
	}
	
	public VariableNestedElement createExports() {
		VariableNestedElement variableNestedElement = new VariableNestedElement();
		exportsVariableNestedElements.add(variableNestedElement);
		return variableNestedElement;
	}
	
	public ParameterNestedElement createParameter() {
		ParameterNestedElement parameterNestedElement = new ParameterNestedElement();
		parameterNestedElements.add(parameterNestedElement);
		return parameterNestedElement;
	}

	@SuppressWarnings("unchecked")
	protected void configureModule() throws EolModelNotFoundException, BuildException, EolModelLoadingException {
		// We can only run these if we're inside a real Eclipse instance:
		// we must avoid these calls if we're running the Ant task inside
		// a JUnit test
		HostManager.getHost().addNativeTypeDelegates(module);
		HostManager.getHost().configureUserInput(module, isGUI());
		
		module.getContext().setExtendedProperties(getExtendedProperties());

		HostManager.getHost().addStopCapabilities(getProject(), module);
		
		EolSystem system = new EolSystem();
		system.setContext(module.getContext());
		module.getContext().setAssertionsEnabled(assertions);
		module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("System", system));
		module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("null", null));
		
		if (setBeans) {
			Project project = getProject();
			module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("project", project));
			addVariables(module.getContext(), 
				project.getProperties(), project.getUserProperties(),
				project.getCopyOfReferences(), project.getCopyOfTargets());
		}
		
		populateModelRepository(false);
		accessParameters();
		useVariables();
	}
	
	protected void addVariables(IEolContext context, @SuppressWarnings("unchecked") Map<String, ?>... variableMaps) {
		for (Map<String, ?> variableMap : variableMaps) {
			for (String key : variableMap.keySet()) {
				module.getContext().getFrameStack().put(Variable.createReadOnlyVariable(key, variableMap.get(key)));				
			}
		}
	}
	
	protected void useResults() throws Exception {
		exportVariables();
		examine();
	}

	protected void populateModelRepository(Boolean mustReload) throws EolModelNotFoundException, EolModelLoadingException {
		ModelRepository repository = module.getContext().getModelRepository();
		if (mustReload) {
			repository.dispose();
		}
		ModelRepository projectRepository = getProjectRepository();
		for (ModelNestedElement modelNestedElement : modelNestedElements) {
			IModel model = projectRepository.getModelByName(modelNestedElement.getRef());
			if (mustReload) {
				model.load();
			}
			ModelReference reference = createReference(model);
			if (modelNestedElement.getAs() != null) {
				reference.setName(modelNestedElement.getAs());
			}
			if (modelNestedElement.getAlias() != null) {
				reference.getAliases().addAll(StringUtil.split(modelNestedElement.getAlias(), ","));
			}
			repository.addModel(reference);
		}
	}

	private ModelReference createReference(IModel model) {
		if (model instanceof IReflectiveModel) {
			return new ReflectiveModelReference((IReflectiveModel)model);
			
		} else {
			return new ModelReference(model);
		}
	}
	
	private void accessParameters() {
		for (ParameterNestedElement parameterNestedElement : parameterNestedElements) {
			module.getContext().getFrameStack().putGlobal(
					Variable.createReadOnlyVariable(
							parameterNestedElement.getName(), 
							parameterNestedElement.getValue()
			));
		}
	}
	
	private void useVariables() throws BuildException {
		for (VariableNestedElement usesVariableNestedElement : usesVariableNestedElements) {
			useVariable(usesVariableNestedElement.getRef(),
					usesVariableNestedElement.getAs(),
					usesVariableNestedElement.isOptional(),
					usesVariableNestedElement.isAnt());
		}		
	}

	private void exportVariables() {
		for (VariableNestedElement exportVariableNestedElement : exportsVariableNestedElements) {
			exportVariable(
					exportVariableNestedElement.getRef(),
					exportVariableNestedElement.getAs(),
					exportVariableNestedElement.isOptional(),
					exportVariableNestedElement.isAnt());
		}
	}

	@Override
	public String getTaskName() {
		if (src != null) {
			return super.getTaskName() + " - " + src.getName();
		}
		else {
			return super.getTaskName();
		}
	}

	@Override
	public void executeImpl() throws BuildException {

		try {
			parseModule();
			if (src!=null && profile) {
				Profiler.INSTANCE.start(src.getName(), "", module);
			}
			configureModule();
			initialize();

			if (fine) {
				module.getContext().getExecutorFactory().addExecutionListener(new ProfilingExecutionListener());
			}
			
			if (!isDebug() || !HostManager.getHost().supportsDebugging()) {
				result = module.execute();
			} else {
				HostManager.getHost().debug(module, getSrc());
			}

			useResults();
			if (src!=null && profile) Profiler.INSTANCE.stop(src.getName());
		}
		catch (Throwable t) {
			if (profile) Profiler.INSTANCE.stop(src.getName());
			if (t instanceof BuildException) {
				throw (BuildException) t;
			}
			else {
				StringWriter sw = new StringWriter();
				t.printStackTrace(new PrintWriter(sw));
				log("EXCEPTION: " + sw.toString(), Project.MSG_ERR);
				throw new BuildException(t);
			}
		}
	}

	private void parseModule() throws Exception {
		module = createModule();
		if (src!=null) {
			module.parse(src);
		}
		else if (uri != null) {
			module.parse(URI.create(uri));
		}
		else {
			module.parse(code);
		}
		if (module.getParseProblems().size() > 0) {
			for (ParseProblem problem : module.getParseProblems()) {
				log(problem.toString(), Project.MSG_ERR);
			}
		}
	}

	public void addText(String msg) {
		if ((msg != null) && (src == null)) {
			code += getProject().replaceProperties(msg);
		}
	}

	public File getSrc() {
		return src;
	}

	public void setSrc(File src) {
		this.src = src;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public boolean isAssertions() {
		return assertions;
	}

	public void setAssertions(boolean assertions) {
		this.assertions = assertions;
	}

	/**
	 * Changes whether Epsilon's graphical user input facilities should be enabled or not.
	 * By default, they are enabled for all tasks except for the EUnit Ant task, in which
	 * they are disabled.
	 */
	public void setGUI(boolean gui) {
		this.isGUI = gui;
	}

	/**
	 * Returns whether Epsilon's graphical user input facilities should be enabled or not.
	 * @see #setGUI(boolean)
	 */
	public boolean isGUI() {
		return isGUI;
	}

	/**
	 * Changes whether the debugger should be used (<code>true</code>) or not (<code>false</code>)
	 * for this module. By default, it is not used.
	 */
	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	/**
	 * Returns whether the debugger will be used (<code>true</code>) or not (<code>false</code>).
	 */
	public boolean isDebug() {
		return isDebug;
	}

	protected void useVariable(final String var, final String as, final boolean optional, boolean ant) {
		Variable usedVariable = null;
		
		if (ant) {
			usedVariable = new Variable(var, getProject().getProperty(var), EolPrimitiveType.String);
		}
		else {
			usedVariable = getProjectStackFrame().get(var);			
		}

		// FIXME : Remove this hack using a proper design!
		if (usedVariable != null) {
			Object value = usedVariable.getValue();
			if (value instanceof IModel) {
				IModel model = (IModel) value;
				ModelReference reference = createReference(model);
				if (as != null) {
					reference.setName(as);
				}
				else {
					reference.setName(var);
				}
				module.getContext().getModelRepository().addModel(reference);
				return;
			}
		}
		// ENDFIXME

		if (usedVariable == null) {
			if (getProject().getProperty(var) != null) {
				usedVariable = new Variable(var, getProject().getProperty(var), EolPrimitiveType.String);
			}
		}

		if (usedVariable == null && !optional) throw new BuildException("Undefined variable " + var);
		if (as != null) {
			usedVariable.setName(as);
		}
		module.getContext().getFrameStack().putGlobal(usedVariable);
	}

	protected void exportVariable(String var, String as, final boolean optional, boolean ant) {
		Variable exportedVariable = module.getContext().getFrameStack().get(var);
		
		// FIXME : 2nd part of the hack
		if (exportedVariable == null) {
			IModel model = module.getContext().getModelRepository().getModelByNameSafe(var);
			if (model != null) {
				exportedVariable = Variable.createReadOnlyVariable(var, model);
			}
		}
		// ENDFIXME

		if (exportedVariable != null) {
			if (as != null) {
				exportedVariable.setName(as);
			}
			if (ant) {
				getProject().setProperty(exportedVariable.getName(), module.getContext().getPrettyPrinterManager().print(exportedVariable.getValue()));
			}
			else {
				getProjectStackFrame().put(exportedVariable);				
			}
		} else {
			if (!optional) {
				throw new BuildException("Variable " + var + " is undefined");
			}
		}
	}
	
	public void setSetBeans(boolean setBeans) {
		this.setBeans = setBeans;
	}
	
	public boolean isSetBeans() {
		return setBeans;
	}
	
	public boolean isFine() {
		return fine;
	}
	
	public void setFine(boolean fine) {
		this.fine = fine;
	}
	
	protected abstract void initialize() throws Exception;

	protected abstract void examine() throws Exception;

	protected abstract IEolModule createDefaultModule() throws Exception;
	
	protected IEolModule createModule() throws Exception {
		IEolModule result = null;
		if (moduleImplementation == null) {
			result = createDefaultModule();
		}
		else {
			result =  createAlternativeModule();
		}
		Set<String> requiredProperties = result.getConfigurationProperties();
		Map<String, Object> props = new HashMap<>(requiredProperties.size());
		for (String rp : requiredProperties) {
			for (ModuleProperty np : properties) {
				if (rp.equals(np.name)) {
					props.put(np.name, np.value);
				}
			}
		}
		result.configure(props);
		return result;
		
	}
	
	public Class<? extends IEolModule> getModuleImplementation() {
		return moduleImplementation;
	}

	public void setModuleImplementation(Class<? extends IEolModule> moduleImplementation) {
		this.moduleImplementation = moduleImplementation;
	}

	/** Ant constructor for nested elements */
    public ModuleProperty createModuleProperty() {                    
    	ModuleProperty property = new ModuleProperty();
    	properties.add(property);
        return property;
    }
    
	/**
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected IEolModule createAlternativeModule() throws InstantiationException, IllegalAccessException {
		IEolModule module = moduleImplementation.newInstance();
		
		return module;
	}
		
}
