package org.ita.neutrino.codeparser;

import org.ita.neutrino.tparsers.abstracttestparser.Action;

/**
 * Classe que representa uma linha de código.
 * 
 * @author Rafael Monico
 *
 */
public interface Statement extends CodeElement {
	
	@Override
	public Block getParent();

	public boolean isBranchStatement();	
	
	public boolean isVariableDeclaration();
	
	public boolean similarDeclaration(Action action);
}
