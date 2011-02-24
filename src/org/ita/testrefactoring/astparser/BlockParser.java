package org.ita.testrefactoring.astparser;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.ita.testrefactoring.debug.ConsoleVisitor;
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
		ConsoleVisitor.showNodes(node);
		if (node instanceof VariableDeclarationStatement) {
			parseVariableDeclaration((VariableDeclarationStatement) node);
		} else if (node instanceof ReturnStatement) {
			parseReturnStatement((ReturnStatement) node);
		}
		
		block.getStatementList().add(null);
	}

	private void parseVariableDeclaration(VariableDeclarationStatement node) throws ParserException {
//		List<ASTNode>nodes = quickVisitor.quickVisit(node);
//		
//		if (!checkNodeStructure(nodes, Type.class, VariableDeclarationFragment.class)) {
//			throw new ParserException("Sintax não suportada.");
//		}
//		
//		Type type = (Type) nodes.get(0);
//		
//		ConsoleVisitor.showNodes(type);
//		
//		type.resolveBinding().getQualifiedName();
	}

//	private boolean checkNodeStructure(List<ASTNode> nodeList, Class<?>... nodes) {
//		if (nodeList.size() != nodes.length) {
//			return false;
//		}
//		
//		for (int i=0; i<nodeList.size(); i++) {
//			if (!(nodeList.get(i).getClass().equals(nodes[i]))) {
//				return false;
//			}
//		}
//		
//		return true;
//	}

	private void parseReturnStatement(ReturnStatement node) {
		// TODO Auto-generated method stub
		
	}

}
