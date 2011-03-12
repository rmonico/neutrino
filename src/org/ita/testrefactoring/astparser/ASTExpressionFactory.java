package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.codeparser.ExpressionFactory;
import org.ita.testrefactoring.codeparser.Type;

class ASTExpressionFactory implements ExpressionFactory {

	@Override
	public ASTLiteralExpression createLiteralExpression(Type type, String value) {
		ASTLiteralExpression expression = new ASTLiteralExpression();

		expression.setValue(value);

		return expression;
	}

}
