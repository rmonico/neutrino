package org.ita.neutrino.testsmells.smells;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ita.neutrino.testsmells.core.EclipseQuickFix;

@ProvidesEclipseQuickFix(CustomEclipseQuickFixProvider.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomEclipseQuickFix {
	Class<? extends EclipseQuickFix> value();
	String title() default "";
	String description() default "";
}
