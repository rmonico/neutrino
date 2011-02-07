package org.ita.testrefactoring.parser;

/**
 * Representação de uma expressão onde o parâmetro é uma invocação de método.
 * 
 * @author kxorroloko
 * 
 */
public class MethodInvocationExpression extends Expression {

	private String value;

	@Override
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
