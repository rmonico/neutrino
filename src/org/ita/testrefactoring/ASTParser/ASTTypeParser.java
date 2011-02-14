package org.ita.testrefactoring.ASTParser;

import org.ita.testrefactoring.metacode.ParserException;

interface ASTTypeParser<T extends ASTType> {

	void setType(T type);

	void parse() throws ParserException;

}
