package org.ita.testrefactoring.codeparser;

public interface Field extends TypeElement, CodeElement {

	NonAccessFieldModifier getNonAccessModifier();
	
	Expression getInitialization();

	Type getFieldType();
}
