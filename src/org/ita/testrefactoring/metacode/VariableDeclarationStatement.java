package org.ita.testrefactoring.metacode;

public class VariableDeclarationStatement extends AbstractStatement {

	private Type type;
	private String name;
	private Expression initialization;
	
	
	public Type getType() {
		return type;
	}
	
	void setType(Type type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	public Expression getInitialization() {
		return initialization;
	}
	
	void setInitialization(Expression initialization) {
		this.initialization = initialization;
	}
}
