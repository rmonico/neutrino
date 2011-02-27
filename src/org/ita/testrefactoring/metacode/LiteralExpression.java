package org.ita.testrefactoring.metacode;


public interface LiteralExpression extends Expression {

	public static final LiteralExpression NULL_EXPRESSION = new NullLiteralExpression();

	public String getValue();

}

class NullLiteralExpression implements LiteralExpression {
	
	@Override
	public String getValue() {
		return "null";
	};

	@Override
	public String toString() {
		return getValue();
	};
	
}

