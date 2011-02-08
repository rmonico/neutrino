package org.ita.testrefactoring.metacode;

public class ReturnStatement extends AbstractStatement {
	private Expression expression;

	
	public Expression getExpression() {
		return expression;
	}

	void setExpression(Expression expression) {
		this.expression = expression;
	}
}
