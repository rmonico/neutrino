package org.ita.testrefactoring.metacode;

public interface Field extends TypeElement {

	NonAccessFieldModifier getNonAccessModifier();
	
	Expression getInitialization();
	
}
