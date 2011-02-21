package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.Method;
import org.ita.testrefactoring.metacode.MethodBlock;

public class ASTMethodBlock extends ASTBlock implements MethodBlock {

	private ASTMethod parentMethod;

	void setParentMethod(ASTMethod parentMethod) {
		this.parentMethod = parentMethod;
	}
	
	@Override
	public Method getParentMethod() {
		return parentMethod;
	}

}
