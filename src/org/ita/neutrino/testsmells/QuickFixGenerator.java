package org.ita.neutrino.testsmells;

import java.lang.annotation.Annotation;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.ita.neutrino.testsmells.core.EclipseQuickFix;
import org.ita.neutrino.testsmells.core.EclipseQuickFixRunner;
import org.ita.neutrino.testsmells.core.Injector;
import org.ita.neutrino.testsmells.smells.IEclipseQuickFixProvider;
import org.ita.neutrino.testsmells.smells.ProvidesEclipseQuickFix;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class QuickFixGenerator implements IMarkerResolutionGenerator {

	private final com.google.inject.Injector injector = Injector.getInjector();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		String smellType = marker.getAttribute("smellType", null);
		
		if (!Strings.isNullOrEmpty(smellType)) {
			try {
				Class<?> testCodeSmell = Class.forName(smellType);
				List<EclipseQuickFix> quickFixes = Lists.newLinkedList();
				
				for (Annotation annotation : testCodeSmell.getAnnotations()) {
					ProvidesEclipseQuickFix provider = annotation.annotationType().getAnnotation(ProvidesEclipseQuickFix.class);
					if (provider != null) {
						IEclipseQuickFixProvider providerImpl = injector.getInstance(provider.value());
						quickFixes.add(providerImpl.getQuickFix(annotation));
					}
				}
				
				IMarkerResolution[] quickFixesImplementations = new IMarkerResolution[quickFixes.size()];
				int count = 0;
				
				for (EclipseQuickFix quickFix : quickFixes) {
					quickFixesImplementations[count++] = new EclipseQuickFixRunner(quickFix);
				}
				return quickFixesImplementations;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return new IMarkerResolution[0];
	}	
}
