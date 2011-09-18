package org.ita.neutrino.astparser;

import org.eclipse.jdt.core.dom.ASTNode;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Block;
import org.ita.neutrino.codeparser.Statement;

class ASTAbstractStatement<T extends ASTNode> extends AbstractCodeElement implements Statement, ASTWrapper<T> {

	private T astObject;
	
	@Override
	public Block getParent() {
		return (Block) super.getParent();
	}
	
	protected void setParent(Block block) {
		parent = block;
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
