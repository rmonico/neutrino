package org.ita.neutrino.astparser;

import org.eclipse.jdt.core.dom.ASTMatcher;
import org.eclipse.jdt.core.dom.ASTNode;
import org.ita.neutrino.codeparser.AbstractCodeElement;
import org.ita.neutrino.codeparser.Block;
import org.ita.neutrino.codeparser.Statement;

class ASTAbstractStatement<T extends ASTNode> extends AbstractCodeElement implements Statement, ASTWrapper<T> {

	private T astObject;
	
	@Override
	public ASTBlock getParent() {
		return (ASTBlock) super.getParent();
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ASTWrapper)) {
			return false;
		}
		
		ASTMatcher astMatcher = new ASTMatcher();
		return this.getASTObject().subtreeMatch(astMatcher, ((ASTWrapper<?>)obj).getASTObject());
	}
	
	@Override
	public boolean isBranchStatement() {
		return this.getASTObject() != null && ( 
			this.getASTObject().getNodeType() == ASTNode.IF_STATEMENT ||
			this.getASTObject().getNodeType() == ASTNode.SWITCH_STATEMENT);
	}
}
