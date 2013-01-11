package org.ita.neutrino.testsmells.smells;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ita.neutrino.refactorings.AbstractEclipseRefactoringAction;

@ProvidesEclipseRefactoring(NeutrinoRefactoringForEclipseProvider.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NeutrinoRefactoringForEclipse {
	Class<? extends AbstractEclipseRefactoringAction> value();
	String title();
	String description();
}
