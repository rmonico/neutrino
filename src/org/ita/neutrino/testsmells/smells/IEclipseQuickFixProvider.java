package org.ita.neutrino.testsmells.smells;

import java.lang.annotation.Annotation;

import org.ita.neutrino.testsmells.core.EclipseQuickFix;

public interface IEclipseQuickFixProvider<T extends Annotation> {
	EclipseQuickFix getQuickFix(T configuration);
}
