package org.ita.testrefactoring.codeparser;

public interface Field extends TypeElement {

	NonAccessFieldModifier getNonAccessModifier();
	
	Expression getInitialization();

	Type getFieldType();

}
