package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Type;

public abstract class ASTAbstractExpression implements Expression, ASTWrapper<org.eclipse.jdt.core.dom.Expression> {

	private org.eclipse.jdt.core.dom.Expression astObject;
	private Type type;
	
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
	
	@Override
	public Type getType() {
		return type;
	}
	
	void setType(Type type) {
		this.type = type;
	}

}
