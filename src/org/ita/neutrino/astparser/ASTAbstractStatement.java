package org.ita.neutrino.astparser;

import org.eclipse.jdt.core.dom.ASTNode;
import org.ita.neutrino.codeparser.Block;
import org.ita.neutrino.codeparser.Statement;

class ASTAbstractStatement<T extends ASTNode> implements Statement, ASTWrapper<T> {

	private Block parentBlock;
	private T astObject;
	
	@Override
	public Block getParent() {
		return parentBlock;
	}
	
	protected void setParent(Block block) {
		parentBlock = block;
	}

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
}
