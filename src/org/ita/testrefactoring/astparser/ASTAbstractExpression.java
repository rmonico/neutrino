package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Type;

public abstract class ASTAbstractExpression<T extends org.eclipse.jdt.core.dom.Expression> implements Expression, ASTWrapper<T> {

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

}
