package org.ita.neutrino.codeparser;

public interface ExpressionFactory {

	LiteralExpression createLiteralExpression(Type type, String value);

}
