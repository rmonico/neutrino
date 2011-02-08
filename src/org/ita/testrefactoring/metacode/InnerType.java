package org.ita.testrefactoring.metacode;

public interface InnerType extends Type, TypeElement {
	Type getParent();
	// Forço que o retorno seja covariante
	InnerElementAccessModifier getAccessModifier();
}
