package org.ita.testrefactoring.astparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

public class QuickVisitor extends ASTVisitor {
	
	private List<ASTNode> nodes = new ArrayList<ASTNode>();
	private ASTNode visitedNode;
	
	public List<ASTNode> quickVisit(ASTNode visitedNode) {
		QuickVisitor visitor = new QuickVisitor();
		
		visitor.setVisitedNode(visitedNode);
		
		visitedNode.accept(visitor);
		
		return visitor.getNodeList();
	}
	
	private void setVisitedNode(ASTNode visitedNode) {
		this.visitedNode = visitedNode;
	}

	public List<ASTNode> getNodeList() {
		return nodes;
	}
	
	@Override
	public boolean preVisit2(ASTNode node) {
		
		boolean isVisitedNode = node == visitedNode;
		
		if (!isVisitedNode) {
			nodes.add(node);
		}
		
		// Só visita os filhos se for o visited node
		return isVisitedNode;
	}
}
