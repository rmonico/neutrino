package org.ita.testrefactoring.metacode;

public class DoStatement extends AbstractStatement {
	private Block block;
	private Expression condition;
	
	
	public Block getBlock() {
		return block;
	}

	void setBlock(Block block) {
		this.block = block;
	}

	public Expression getCondition() {
		return condition;
	}

	void setCondition(Expression condition) {
		this.condition = condition;
	}
}
