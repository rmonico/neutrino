package org.ita.neutrino.codeparser;

public interface Field extends TypeElement, CodeElement {

	NonAccessFieldModifier getNonAccessModifier();
	
	Expression getInitialization();

	Type getFieldType();
	
}
