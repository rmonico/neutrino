package org.ita.neutrino.testsmells;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.ita.neutrino.testsmells.core.Injector;

public class TestSmellDetectionBuilder extends IncrementalProjectBuilder {
	
	public static final String BUILDER_ID = TestSmellDetectionBuilder.class.getCanonicalName();
	
	public TestSmellDetectionBuilder() {
		super();
	}
		
	@Override
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		
		IProject project = this.getProject();
		if (!project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			return null;
		}
		
		Injector.createEclipseTestSmellDetector().run(
				this.getProject(), this.getDelta(this.getProject()), monitor);
		return null;
	}
}