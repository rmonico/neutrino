package org.ita.testrefactoring.ASTParser;

class ClassParser implements ASTTypeParser {
	
	private ASTType type;

	@Override
	public void setType(ASTType type) {
		this.type = type;
	}

	@Override
	public void parse() {
		type.getASTObject();
	}

}
