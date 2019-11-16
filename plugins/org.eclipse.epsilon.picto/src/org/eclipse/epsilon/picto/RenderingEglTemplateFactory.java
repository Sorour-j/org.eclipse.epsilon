package org.eclipse.epsilon.picto;

import java.io.File;

import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;

public class RenderingEglTemplateFactory extends EglTemplateFactory {
	
	protected ViewTree viewTree = new ViewTree("");
	protected File outputRoot = null;
	
	public RenderingEglTemplateFactory(File outputRoot) {
		super();
		this.outputRoot = outputRoot;
	}
	
	@Override
	protected EglTemplate createTemplate(EglTemplateSpecification spec) throws Exception {
		RenderingEglTemplate template = new RenderingEglTemplate(spec, context, outputRoot.toURI());
		template.setViewTree(viewTree);
		return template;
	}
	
	public ViewTree getViewTree() {
		return viewTree;
	}
	
	public File getOutputRoot() {
		return outputRoot;
	}
	
}
