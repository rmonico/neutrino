package org.ita.testrefactoring.metacode;

public class SwitchCase {
	private SwitchStatement switchStatement;
	private LiteralExpression expression;
	private Block block;

	public SwitchStatement getSwitchStatement() {
		return switchStatement;
	}

	void setSwitchStatement(SwitchStatement switchStatement) {
		this.switchStatement = switchStatement;
	}

	public LiteralExpression getExpression() {
		return expression;
	}

	void setExpression(LiteralExpression expression) {
		this.expression = expression;
	}

	public Block getBlock() {
		return block;
	}

	void setBlock(Block block) {
		this.block = block;
	}
	
	
}
