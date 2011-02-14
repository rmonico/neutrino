package org.ita.testrefactoring.metacode;

public interface InnerType extends Type, TypeElement {
	
	// Forço que o retorno seja covariante
	InnerElementAccessModifier getAccessModifier();
}
