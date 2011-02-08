package org.ita.testrefactoring.metacode;

public class MethodInvocationStatement extends MethodInvocation {

	private Block parentBlock;
	
	@Override
	public Block getBlock() {
		return parentBlock;
	}
	
	void setBlock(Block parent) {
		parentBlock = parent;
	}

}
