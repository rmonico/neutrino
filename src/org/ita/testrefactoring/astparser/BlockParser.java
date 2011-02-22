package org.ita.testrefactoring.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.ita.testrefactoring.metacode.ParserException;

class BlockParser {

	private ASTBlock block;
	QuickVisitor quickVisitor = new QuickVisitor();
	
	public void setBlock(ASTBlock block) {
		this.block = block;
	}

	public void parse() throws ParserException {
		List<ASTNode> nodes = quickVisitor.quickVisit(block.getASTObject());
		
		for (ASTNode node : nodes) {
			parseStatement(node);
		}
	}

	private void parseStatement(ASTNode node) throws ParserException {
		if (node instanceof VariableDeclarationStatement) {
			parseVariableDeclaration((VariableDeclarationStatement) node);
		} else if (node instanceof ReturnStatement) {
			parseReturnStatement((ReturnStatement) node);
		}
	}

	private void parseVariableDeclaration(VariableDeclarationStatement node) throws ParserException {
		List<ASTNode>nodes = quickVisitor.quickVisit(node);
		
		if (nodes.get(0) instanceof Type) {
			Type type = (Type) nodes.get(0);
			
			type.resolveBinding().getQualifiedName();
		} else {
			throw new ParserException("Sintax não suportada.");
		}
	}

	private void parseReturnStatement(ReturnStatement node) {
		// TODO Auto-generated method stub
		
	}

}
