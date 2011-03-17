package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.codeparser.LiteralExpression;

public class ASTLiteralExpression extends ASTAbstractExpression<org.eclipse.jdt.core.dom.PrefixExpression> implements LiteralExpression {

	private String value;

	ASTLiteralExpression() {
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
