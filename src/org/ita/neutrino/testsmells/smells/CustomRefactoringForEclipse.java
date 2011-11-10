package org.ita.neutrino.testsmells.smells;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ita.neutrino.testsmells.core.EclipseRefactoring;

@ProvidesEclipseQuickFix(CustomRefactoringForEclipseProvider.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomRefactoringForEclipse {
	Class<? extends EclipseRefactoring> value();
	String title() default "";
	String description() default "";
}
