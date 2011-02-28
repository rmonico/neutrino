package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Expression;

public abstract class ASTAbstractExpression implements Expression, ASTWrapper<org.eclipse.jdt.core.dom.Expression> {

	private org.eclipse.jdt.core.dom.Expression astObject;
	
	@Override
	public void setASTObject(org.eclipse.jdt.core.dom.Expression astObject) {
		this.astObject = astObject;
	}

	@Override
	public org.eclipse.jdt.core.dom.Expression getASTObject() {
		return astObject;
	}

	@Override
	public String toString() {
		return getASTObject().toString();
	}

}
