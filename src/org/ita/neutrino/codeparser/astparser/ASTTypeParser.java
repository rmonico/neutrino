package org.ita.neutrino.codeparser.astparser;

import org.ita.neutrino.codeparser.ParserException;

interface ASTTypeParser<T extends ASTType> {

	void setType(T type);

	void parse() throws ParserException;

}
