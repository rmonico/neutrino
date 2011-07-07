package org.ita.neutrino.extractfinalizationmethod;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;

public class ExtractFinalizationMethodRefactoring extends AbstractRefactoring {

	private TestSuite targetSuite;
	private List<TestStatement> commomStatements;

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestSuite)) || (getTargetFragment() == null)) {
			problems.add("Selection must be a test suite (selection: \"" + getTargetFragment() + "\").");
		} else {
			targetSuite = (TestSuite) getTargetFragment();

			commomStatements = listCommonStatements(targetSuite, false);

			if (commomStatements.isEmpty()) {
				problems.add("Methods have no commom initialization. Unable to extract initialization method.");
			}
		}

		return problems;
	}

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
		List<TestStatement> commomStatements = new ArrayList<TestStatement>();

		List<? extends TestMethod> testMethods = suite.getTestMethodList();

		// Utiliza a lista de statements do primeiro método como base para
		// comparar com os demais
		commomStatements.addAll(testMethods.get(0).getStatements());

		// iterar por todos, exceto o primeiro
		for (int i = 1; i < testMethods.size(); i++) {
			TestMethod testMethod = testMethods.get(i);

			// Compara duas lista de elementos de teste
			// Cálculo do tamanho da menor lista
			List<? extends TestStatement> smallerList;
			List<? extends TestStatement> greaterList;

			if (testMethod.getStatements().size() < commomStatements.size()) {
				smallerList = testMethod.getStatements();
				greaterList = commomStatements;
			} else {
				smallerList = commomStatements;
				greaterList = testMethod.getStatements();
			}

			// Comparar os statements da lista base com os do método sendo
			// testado, testMethod
			for (int j = 0; j < smallerList.size(); j++) {
				TestStatement baseStatement;
				TestStatement testingStatement;

				if (fromBegin) {
					baseStatement = smallerList.get(j);
					testingStatement = greaterList.get(j);
				} else {
					baseStatement = smallerList.get(smallerList.size() - j - 1);
					testingStatement = greaterList.get(greaterList.size() - j - 1);
				}

				if (!baseStatement.equals(testingStatement)) {
					if (fromBegin) {
						commomStatements = commomStatements.subList(0, j);
					} else {
						commomStatements = commomStatements.subList(commomStatements.size() - j, commomStatements.size());
					}
					
					break;
				}
			}
		}

		return commomStatements;
	}

	@Override
	protected void doRefactor() throws RefactoringException {

		TestMethod afterMethod;

		if (targetSuite.getAfterMethodList().isEmpty()) {
			afterMethod = targetSuite.createNewAfterTestsMethod();
		} else {
			List<? extends TestMethod> afterMethodList = targetSuite.getAfterMethodList();
			afterMethod = afterMethodList.get(0);
		}

		afterMethod.addStatements(commomStatements, 0);

		for (TestMethod testMethod : targetSuite.getTestMethodList()) {
			testMethod.removeStatements(testMethod.getStatements().size()-commomStatements.size(), commomStatements.size());
		}
	}

}
