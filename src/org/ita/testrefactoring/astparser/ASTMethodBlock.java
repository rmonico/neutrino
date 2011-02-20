package org.ita.testrefactoring.astparser;

import org.eclipse.jdt.core.dom.Block;
import org.ita.testrefactoring.metacode.Method;
import org.ita.testrefactoring.metacode.MethodBlock;

public class ASTMethodBlock extends ASTBlock implements MethodBlock, ASTWrapper<Block> {

	private ASTMethod parentMethod;
	private Block astObject;
	
	void setParentMethod(ASTMethod parentMethod) {
		this.parentMethod = parentMethod;
	}
	
	@Override
	public Method getParentMethod() {
		return parentMethod;
	}

	@Override
	public void setASTObject(Block astObject) {
		this.astObject = astObject;
	}
	
	public Block getASTObject() {
		return astObject;
	}

}
