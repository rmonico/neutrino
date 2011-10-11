package org.ita.neutrino.testsmells;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;

// import com.google.inject.Guice;
// import com.google.inject.Injector;

public class TestSmellDetectionBuilder extends IncrementalProjectBuilder {
	
	public static final String BUILDER_ID = TestSmellDetectionBuilder.class.getCanonicalName();
//	private static final Injector guiceInjector = Guice.createInjector(new TestCodeAnalysisGuiceModule());
	
	public TestSmellDetectionBuilder() {
		super();
	}
		
	@Override
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		new TestSmellDetector().run(this.getProject(), this.getDelta(this.getProject()), monitor);
		return null;
	}

}