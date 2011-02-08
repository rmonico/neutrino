package org.ita.testrefactoring.metacode;

public class WhileStatement extends AbstractStatement {
	private Expression condition;
	private Block block;
	
	public Expression getCondition() {
		return condition;
	}
	
	void setCondition(Expression condition) {
		this.condition = condition;
	}
	
	public Block getBlock() {
		return block;
	}
	
	void setBlock(Block block) {
		this.block = block;
	}
}
