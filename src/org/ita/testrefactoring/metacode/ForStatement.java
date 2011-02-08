package org.ita.testrefactoring.metacode;

public class ForStatement extends AbstractStatement {
	private Statement declaration;
	private Expression condition;
	private Statement increment;
	private Block block;
	
	
	public Statement getDeclaration() {
		return declaration;
	}
	
	void setDeclaration(Statement declaration) {
		this.declaration = declaration;
	}
	
	public Expression getCondition() {
		return condition;
	}
	
	void setCondition(Expression condition) {
		this.condition = condition;
	}
	
	public Statement getIncrement() {
		return increment;
	}
	
	void setIncrement(Statement increment) {
		this.increment = increment;
	}

	public Block getBlock() {
		return block;
	}

	void setBlock(Block block) {
		this.block = block;
	}

}
