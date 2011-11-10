package org.ita.neutrino.testsmells.smells;

import java.lang.annotation.Annotation;

import org.ita.neutrino.testsmells.core.EclipseRefactoring;

public interface IEclipseRefactoringProvider<T extends Annotation> {
	EclipseRefactoring getRefactoringFromAnnotation(T annotation);
}
