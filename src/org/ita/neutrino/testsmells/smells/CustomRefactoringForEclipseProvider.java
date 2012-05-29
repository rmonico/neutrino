package org.ita.neutrino.testsmells.smells;

import org.eclipse.jface.viewers.ISelection;
import org.ita.neutrino.eclipseaction.ActionException;
import org.ita.neutrino.testsmells.core.EclipseRefactoring;
import org.ita.neutrino.testsmells.core.Injector;

import com.google.common.base.Strings;

public class CustomRefactoringForEclipseProvider implements IEclipseRefactoringProvider<org.ita.neutrino.testsmells.smells.CustomRefactoringForEclipse>  {
	public EclipseRefactoring getRefactoringFromAnnotation(final org.ita.neutrino.testsmells.smells.CustomRefactoringForEclipse configuration) {
		
		final EclipseRefactoring impl = Injector.getInjector().getInstance(configuration.value());
		
		return new EclipseRefactoring() {
			
			@Override
			public String title() {
				if (Strings.isNullOrEmpty(configuration.title())) {
					return impl.title();
				}
				return configuration.title();
			}
			
			@Override
			public void run(ISelection ensureMarkerSelected) throws ActionException {
				impl.run(ensureMarkerSelected);
			}
			
			@Override
			public String description() {
				if (Strings.isNullOrEmpty(configuration.description())) {
					return impl.description();					
				}
				return configuration.description();
			}
		};
	}
}
