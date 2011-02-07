package org.ita.testrefactoring.parser;

public class LiteralStringExpression extends Expression {
	
	private String value;

	@Override
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
