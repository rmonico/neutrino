package org.ita.neutrino.astparser;

import org.ita.neutrino.codeparser.ExpressionFactory;
import org.ita.neutrino.codeparser.Type;

class ASTExpressionFactory implements ExpressionFactory {

	@Override
	public ASTLiteralExpression createLiteralExpression(Type type, String value) {
		ASTLiteralExpression expression = new ASTLiteralExpression();

		expression.setValue(value);

		return expression;
	}

}
