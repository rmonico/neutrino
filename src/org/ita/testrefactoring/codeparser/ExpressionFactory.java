package org.ita.testrefactoring.codeparser;

public interface ExpressionFactory {

	LiteralExpression createLiteralExpression(Type type, String value);

}
