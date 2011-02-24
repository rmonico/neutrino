package org.ita.testrefactoring.metacode;

public interface VariableDeclarationStatement extends Statement {

	public Type getVariableType();
	
	public String getVariableName();
	
	public Expression getInitialization();
}
