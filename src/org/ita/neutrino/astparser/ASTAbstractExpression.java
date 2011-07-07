package org.ita.neutrino.astparser;

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

}
