	package org.ita.neutrino.testsmells;

import java.lang.annotation.Annotation;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.ita.neutrino.testsmells.core.EclipseRefactoring;
import org.ita.neutrino.testsmells.core.EclipseQuickFixRunner;
import org.ita.neutrino.testsmells.core.Injector;
import org.ita.neutrino.testsmells.smells.IEclipseRefactoringProvider;
import org.ita.neutrino.testsmells.smells.ProvidesEclipseRefactoring;

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
				List<EclipseRefactoring> quickFixes = Lists.newLinkedList();
				
				for (Annotation annotation : testCodeSmell.getAnnotations()) {
					ProvidesEclipseRefactoring provider = annotation.annotationType().getAnnotation(ProvidesEclipseRefactoring.class);
					if (provider != null) {
						IEclipseRefactoringProvider providerImpl = injector.getInstance(provider.value());
						quickFixes.add(providerImpl.getRefactoringFromAnnotation(annotation));
					}
				}
				
				IMarkerResolution[] quickFixesImplementations = new IMarkerResolution[quickFixes.size()];
				int count = 0;
				
				for (EclipseRefactoring quickFix : quickFixes) {
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
