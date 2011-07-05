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

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestSuite)) || (getTargetFragment() == null)) {
			problems.add("Selection must be a test suite (selection: \"" + getTargetFragment() + "\").");
		} else {
			targetSuite = (TestSuite) getTargetFragment();

			// TODO: Listar as linhas em comum no início de cada um dos blocos
			// de código, se não houver devolver erro
		}

		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		List<TestStatement> commomStatements = listCommonStatements();
		
		System.out.println(commomStatements);
	}

	private List<TestStatement> listCommonStatements() {
		List<TestStatement> commomStatements = new ArrayList<TestStatement>();

		List<? extends TestMethod> testMethods = targetSuite.getTestMethodList();

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

}
