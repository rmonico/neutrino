package org.ita.testrefactoring.astparser;

import org.ita.testrefactoring.metacode.ConcreteMethod;

public class ASTConcreteMethod extends ASTMethod implements ConcreteMethod {
	
	private ASTMethodBlock body = createBlock();

	ASTMethodBlock createBlock() {
		ASTMethodBlock methodBlock = new ASTMethodBlock();
		
		methodBlock.setParentMethod(this);
		
		return methodBlock;
	}

	@Override
	public ASTMethodBlock getBody() {
		return body;
	}

}
