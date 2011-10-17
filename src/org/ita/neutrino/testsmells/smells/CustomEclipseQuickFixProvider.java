package org.ita.neutrino.testsmells.smells;

import org.eclipse.jface.viewers.ISelection;
import org.ita.neutrino.eclipseaction.ActionException;
import org.ita.neutrino.testsmells.core.EclipseQuickFix;
import org.ita.neutrino.testsmells.core.Injector;

import com.google.common.base.Strings;

public class CustomEclipseQuickFixProvider implements IEclipseQuickFixProvider<org.ita.neutrino.testsmells.smells.CustomEclipseQuickFix>  {
	public EclipseQuickFix getQuickFix(final org.ita.neutrino.testsmells.smells.CustomEclipseQuickFix configuration) {
		
		final EclipseQuickFix impl = Injector.getInjector().getInstance(configuration.value());
		
		return new EclipseQuickFix() {
			
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
