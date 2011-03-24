package org.ita.neutrino.codeparser;


public interface LiteralExpression extends Expression {

	public static final LiteralExpression NULL_EXPRESSION = new NullLiteralExpression();

}

class NullLiteralExpression implements LiteralExpression {
	
	@Override
	public String getValue() {
		return "null";
	};

	@Override
	public String toString() {
		return getValue();
	}

	@Override
	public Type getType() {
		return null;
	}

}

