package org.ita.neutrino.extractinitializationmethod;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;

public class ExtractInitializationMethodRefactoring extends AbstractRefactoring {

	private TestSuite targetSuite;
	private List<TestStatement> commomStatements;

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestSuite)) || (getTargetFragment() == null)) {
			problems.add("Selection must be a test suite (selection: \"" + getTargetFragment() + "\").");
		} else {
			targetSuite = (TestSuite) getTargetFragment();

			commomStatements = listCommonStatements(targetSuite);
			
			if (commomStatements.isEmpty()) {
				problems.add("Methods have no commom initialization. Unable to extract initialization method.");
			}
		}

		return problems;
	}

	private List<TestStatement> listCommonStatements(TestSuite suite) {
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
			int smallerListSize;
			
			if (testMethod.getStatements().size() < commomStatements.size()) {
				smallerListSize = testMethod.getStatements().size();
			} else {
				smallerListSize = commomStatements.size();
			}
				
			// Comparar os statements da lista base com os do método sendo
			// testado, testMethod
			for (int j=0; j<smallerListSize; j++) {
				TestStatement baseStatement = commomStatements.get(j);
				TestStatement testingStatement = testMethod.getStatements().get(j);
				
				if (!baseStatement.equals(testingStatement)) {
					commomStatements = commomStatements.subList(0, j);
					break;
				}
			}
		}

		return commomStatements;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		
		TestMethod beforeMethod;
		
		if (targetSuite.getBeforeMethodList().isEmpty()) {
			beforeMethod = targetSuite.createNewBeforeTestsMethod();
		} else {
			List<? extends TestMethod> beforeMethodList = targetSuite.getBeforeMethodList();
			beforeMethod = beforeMethodList.get(beforeMethodList.size()-1);
		}

		// adiciona os novos statements ao final do método
		beforeMethod.addStatements(commomStatements, -1);
		
		for (TestMethod testMethod : targetSuite.getTestMethodList()) {
			testMethod.removeStatements(0, commomStatements.size());
		}
	}

}
