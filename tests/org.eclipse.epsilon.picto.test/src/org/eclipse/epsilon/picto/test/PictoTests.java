package org.eclipse.epsilon.picto.test;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewRenderer;
import org.eclipse.epsilon.picto.ViewTree;
import org.eclipse.epsilon.picto.dom.PictoPackage;
import org.eclipse.epsilon.picto.source.StandalonePictoSource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.junit.BeforeClass;

public class PictoTests {
	
	@BeforeClass
	public static void setup() {
		EPackage.Registry.INSTANCE.put(PictoPackage.eINSTANCE.getNsURI(), PictoPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
	}
	
	protected PictoView createPictoView() {
		PictoView pictoView = new PictoView();
		pictoView.setViewRenderer(new ViewRenderer(new Browser(new Shell(), SWT.NONE)));
		return pictoView;
	}
	
	protected ViewTree getViewTree(String path) throws Exception {
		return new TestPictoSource(new File(path)).getViewTree(new MockIEditor());
	}
	
	
	class TestPictoSource extends StandalonePictoSource {
		
		protected MockIFile iFile = null;
		
		public TestPictoSource(File file) {
			this.iFile = new MockIFile(file);
		}
		
		@Override
		protected IFile waitForFile(IEditorPart editorPart) {
			return iFile;
		}
		
		@Override
		protected IFile getFile(IEditorPart editorPart) {
			return iFile;
		}
		
		@Override
		public boolean supports(IEditorPart editorPart) {
			return true;
		}
		
	}

	
}
