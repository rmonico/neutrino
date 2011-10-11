package org.ita.neutrino.testsmells;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.ita.neutrino.testsmells.core.EclipseQuickFix;
import org.ita.neutrino.testsmells.core.EclipseQuickFixRunner;
import org.ita.neutrino.testsmells.core.Injector;
import org.ita.neutrino.testsmells.smells.TestCodeSmell;

import com.google.common.base.Strings;

public class QuickFixGenerator implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		String smellType = marker.getAttribute("smellType", null); 
		if (!Strings.isNullOrEmpty(smellType)) {
			TestCodeSmell testCodeSmell = instantiateTestCodeSmell(smellType);
			EclipseQuickFix[] quickFixes = testCodeSmell.getQuickFixes();
			IMarkerResolution[] quickFixesImplementations = new IMarkerResolution[quickFixes.length];
			for (int i = 0; i < quickFixes.length; ++i) {
				quickFixesImplementations[i] = 
					new EclipseQuickFixRunner(quickFixes[i]);
			}
			return quickFixesImplementations;
		}
		return new IMarkerResolution[0];
	}	
	
	private TestCodeSmell instantiateTestCodeSmell(String smellType) {
		try {
			return (TestCodeSmell) Injector.getInjector().getInstance(Class.forName(smellType));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
