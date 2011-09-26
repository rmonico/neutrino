package org.ita.neutrino.codeparser;

public interface VariableDeclarationStatement extends Statement {

	public Type getVariableType();
	
	public String getVariableName();
	
	public Expression getInitialization();
	
	public void transformInExpression();
	
	public void transformInExpression(String variableName);
}
