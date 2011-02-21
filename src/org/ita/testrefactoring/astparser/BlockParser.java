package org.ita.testrefactoring.astparser;

import org.eclipse.jdt.core.dom.ASTVisitor;

class BlockParser {

	private BlockVisitor visitor = new BlockVisitor();

	
	private static class BlockVisitor extends ASTVisitor {

		private ASTBlock block;
		
	}

	public void setBlock(ASTBlock block) {
		visitor.block = block;
	}

	public void parse() {
		ASTBlock block = visitor.block;
		
		block.getASTObject().accept(visitor);
	}

}
