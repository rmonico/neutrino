package org.ita.testrefactoring.metacode;

public class IfStatement extends AbstractStatement {
	private Expression condition;
	private Block ifBlock;
	private Block elseBlock;
	
	
	public Expression getCondition() {
		return condition;
	}
	
	void setCondition(Expression condition) {
		this.condition = condition;
	}
	
	public Block getIfBlock() {
		return ifBlock;
	}
	
	void setIfBlock(Block ifBlock) {
		this.ifBlock = ifBlock;
	}
	
	public Block getElseBlock() {
		return elseBlock;
	}
	
	void setElseBlock(Block elseBlock) {
		this.elseBlock = elseBlock;
	}
}
