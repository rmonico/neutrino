package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.ConcreteMethod;

public class ASTConcreteMethod extends ASTMethod implements ConcreteMethod {
	
	private ASTBlock body = createBlock();

	ASTBlock createBlock() {
		ASTBlock methodBlock = new ASTBlock();
		
		methodBlock.setParentMethod(this);
		
		return methodBlock;
	}

	@Override
	public ASTBlock getBody() {
		return body;
	}

}
