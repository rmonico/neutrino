package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.LiteralExpression;


public class ASTLiteralExpression extends AbstractASTExpression implements LiteralExpression {

	private String value;

	public ASTLiteralExpression() {
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	protected void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
