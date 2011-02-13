package org.ita.testrefactoring.ASTParser;

import org.ita.testrefactoring.metacode.Interface;

public class ASTInterface extends ASTType implements Interface {

	private Interface parent;

	@Override
	public Interface getParent() {
		return parent;
	}
	
	protected void setParent(Interface _interface) {
		this.parent = _interface;
	}

}
