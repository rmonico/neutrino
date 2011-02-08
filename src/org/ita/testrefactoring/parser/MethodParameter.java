package org.ita.testrefactoring.parser;

import org.ita.testrefactoring.metacode.Expression;


public class MethodParameter {
	private String type;
	private Expression expression;
	
	/**
	 * Nome qualificado da classe.
	 * @return
	 */
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	public Expression getExpression() {
		return expression;
	}
}
