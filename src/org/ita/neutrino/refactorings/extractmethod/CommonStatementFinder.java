package org.ita.neutrino.refactorings.extractmethod;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class CommonStatementFinder {
	public List<TestStatement> listCommonStatements(TestSuite suite, boolean fromBegin) {
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
}
