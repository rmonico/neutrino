package org.ita.neutrino.testsmells.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;
import org.ita.neutrino.eclipseaction.ActionException;

import com.google.common.collect.ImmutableList;

public class EclipseQuickFixRunner implements IMarkerResolution2 {

	private final EclipseQuickFix quickFix;
	
	public EclipseQuickFixRunner(EclipseQuickFix quickFix) {
		this.quickFix = quickFix;
	}
	
	@Override
	public String getLabel() {
		return quickFix.title();
	}

	@Override
	public void run(IMarker marker) {
		// AddExplanationJUnit4 action = new AddExplanationJUnit4();					
		try {
			quickFix.run(IDEUtils.ensureMarkerSelected(marker));
			maybeRemoveMarker(marker);
		} catch (ActionException e) {
			throw new RuntimeException("Quick-fix failed: " + e.getMessage(), e);
		}
	}

	@Override
	public String getDescription() {
		return quickFix.description();
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	private void maybeRemoveMarker(IMarker marker) {
		try {
			Injector.createEclipseTestSmellDetector().run(
					ImmutableList.of(JavaCore.createCompilationUnitFrom((IFile) marker.getResource())), 
					null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
