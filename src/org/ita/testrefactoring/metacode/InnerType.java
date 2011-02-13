package org.ita.testrefactoring.metacode;

public interface InnerType extends Type, TypeElement {
	
	// Forço o retorno seja covariante
	InnerElementAccessModifier getAccessModifier();
}
