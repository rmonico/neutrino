package org.ita.neutrino.testsmells.smells;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ProvidesEclipseRefactoring {
	Class<? extends IEclipseRefactoringProvider<? extends Annotation>> value();
}
