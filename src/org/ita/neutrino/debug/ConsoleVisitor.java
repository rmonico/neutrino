package org.ita.neutrino.debug;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.ita.neutrino.codeparser.astparser.QuickVisitor;


/**
 * Classe criada para finalidade de debug, mostra os nós imeadiatamente inferiores ao nó passado como parâmetro.
 * @author Rafael Monico
 *
 */
public class ConsoleVisitor  {
	private static String getIdentString(int identLevel) {
		StringBuilder s = new StringBuilder();
		
		for (int i=0; i<identLevel; i++) {
			s.append("  ");
		}
		
		return s.toString();
	}
	
	private static void showNodes(int identacao, ASTNode visitedNode) {
		List<ASTNode> nodes = new QuickVisitor().quickVisit(visitedNode);
		
		String line = getIdentString(identacao) + visitedNode.toString();
		
		if (line.charAt(line.length()-1) == '\n') {
			line = line.substring(0, line.length()-1);
		}
		
		System.out.println(line + " (" + visitedNode.getClass() + ")");
		
		for (ASTNode node : nodes) {
			showNodes(identacao+1, node);
		}
		
	}
	
	public static void showNodes(ASTNode visitedNode) {
		showNodes(0, visitedNode);
		
		System.out.println();
		System.out.println();
	}

}
