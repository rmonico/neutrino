package org.ita.testrefactoring.metacode;

public interface VariableDeclarationStatement extends Statement {

	public Type getType();
	
	public String getName();
	
	public Expression getInitialization();
}
