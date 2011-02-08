package org.ita.testrefactoring.metacode;

public class AbstractStatement implements Statement {

	private Block parentBlock;

	@Override
	public Block getParent() {
		return parentBlock;
	}
	
	void setParent(Block parent) {
		parentBlock = parent;
	}

}
