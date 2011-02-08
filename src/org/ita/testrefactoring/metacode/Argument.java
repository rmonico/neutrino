package org.ita.testrefactoring.metacode;

/**
 * Argumento: o que o método recebe.
 * @author Rafael Monico
 *
 */
public class Argument {
	private String name;
	private Type type;
	
	public String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	public Type getType() {
		return type;
	}
	
	void setType(Type type) {
		this.type = type;
	}
}
