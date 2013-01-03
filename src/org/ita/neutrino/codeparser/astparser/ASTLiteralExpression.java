package org.ita.neutrino.codeparser.astparser;

import org.ita.neutrino.codeparser.LiteralExpression;

public class ASTLiteralExpression extends ASTAbstractExpression<org.eclipse.jdt.core.dom.Expression> implements LiteralExpression {

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
