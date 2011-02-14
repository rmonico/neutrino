package org.ita.testrefactoring.ASTParser;

interface ASTTypeParser<T extends ASTType> {

	void setType(T type);

	void parse();

}
