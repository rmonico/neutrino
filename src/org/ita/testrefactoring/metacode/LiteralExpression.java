package org.ita.testrefactoring.metacode;


public class LiteralExpression implements Expression {
	
	private String value;

	public String getValue() {
		return value;
	}
	
	void setValue(String value) {
		this.value = value;
	}

}
