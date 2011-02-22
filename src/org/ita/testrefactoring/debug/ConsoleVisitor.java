package org.ita.testrefactoring.debug;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.ita.testrefactoring.astparser.QuickVisitor;


/**
 * Classe criada para finalidade de debug, mostra os nós imeadiatamente inferiores ao nó passado como parâmetro.
 * @author Rafael Monico
 *
 */
public class ConsoleVisitor  {
	public static void showNodes(ASTNode visitedNode) {
		List<ASTNode> nodes = new QuickVisitor().quickVisit(visitedNode);
		
		System.out.println("--- " + visitedNode.toString() + " ---");
		System.out.println("Class: " + visitedNode.getClass());
		System.out.println("Subnodes:");

		for (ASTNode node : nodes) {
			System.out.println("Class: " + node.getClass());
			System.out.println("--- " + node.toString() + " ---");
			System.out.println();
		}
		
		System.out.println("--- " + visitedNode.toString() + " ---");
		System.out.println();
	}

}
