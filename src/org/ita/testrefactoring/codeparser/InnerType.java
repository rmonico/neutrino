package org.ita.testrefactoring.codeparser;

public interface InnerType extends Type, TypeElement {
	
	// Forço que o retorno seja covariante
	InnerElementAccessModifier getAccessModifier();
}
