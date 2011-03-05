package org.ita.testrefactoring.codeparser;

public interface VariableDeclarationStatement extends Statement {

	public Type getVariableType();
	
	public String getVariableName();
	
	public Expression getInitialization();
}
