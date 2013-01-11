package org.ita.neutrino.refactorings.extractmethod;

import java.util.List;

import org.ita.neutrino.refactorings.AbstractRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public abstract class AbstractExtractMethodRefactoring extends AbstractRefactoring {

	/**
	 * Lista os statements comuns a todos os métodos.
	 * 
	 * @param suite
	 * @param fromBegin
	 *            Indica se a procura deve começar do início (true) ou do final
	 *            (false).
	 * @return
	 */
	protected List<TestStatement> listCommonStatements(TestSuite suite, boolean fromBegin) {
		return new CommonStatementFinder().listCommonStatements(suite, fromBegin);
	}
}
