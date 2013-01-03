package org.ita.neutrino.codeparser.astparser;

import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.Type;

public abstract class ASTAbstractExpression<T extends org.eclipse.jdt.core.dom.ASTNode> extends AbstractCodeElement implements Expression, ASTWrapper<T> {

	private T astObject;
	private Type type;
	
	@Override
	public void setASTObject(T astObject) {
		this.astObject = astObject;
	}

	@Override
	public T getASTObject() {
		return astObject;
	}

	@Override
	public String toString() {
		return getASTObject().toString();
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	void setType(Type type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ASTWrapper)) {
			return false;
		}
		
		ASTMatcher astMatcher = new ASTMatcher();
		return this.getASTObject().subtreeMatch(astMatcher, ((ASTWrapper<?>)obj).getASTObject());
	}
	
	@Override
	public boolean isLogicalExpression() {
		final boolean[] returnValue = new boolean[] { false };
		
		this.getASTObject().accept(new ASTVisitor() {
			@Override
			public boolean visit(InfixExpression node) {
				if (node.getOperator() == Operator.CONDITIONAL_AND ||
					node.getOperator() == Operator.CONDITIONAL_OR) {
					returnValue[0] = true;
				}
				return false;
			}
		});
		
		return returnValue[0];
	}
}
