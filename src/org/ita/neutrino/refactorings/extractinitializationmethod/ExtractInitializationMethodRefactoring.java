package org.ita.neutrino.refactorings.extractinitializationmethod;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.codeparser.VariableDeclarationStatement;
import org.ita.neutrino.refactorings.abstracrefactoring.RefactoringException;
import org.ita.neutrino.refactorings.extractmethod.AbstractExtractMethodRefactoring;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class ExtractInitializationMethodRefactoring extends AbstractExtractMethodRefactoring {

	private TestSuite targetSuite;
	private List<TestStatement> commomStatements;

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestSuite)) || (getTargetFragment() == null)) {
			problems.add("Selection must be the class name of the test suite (selection: \"" + getTargetFragment() + "\").");
		} else {
			targetSuite = (TestSuite) getTargetFragment();

			commomStatements = listCommonStatements(targetSuite, true);

			if (commomStatements.isEmpty()) {
				problems.add("Methods have no commom initialization. Unable to extract initialization method.");
			}
		}

		if (problems != null && problems.size() > 0) {
			problems.add("Note: Select the class name of the test suite, press extract initialization method, all test methods must have at least the first line in commom, the commom lines will be extracted to a setup method.");
		}
		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {

		TestMethod beforeMethod;

		if (targetSuite.getBeforeMethodList().isEmpty()) {
			beforeMethod = targetSuite.createNewBeforeTestsMethod();
		} else {
			List<? extends TestMethod> beforeMethodList = targetSuite.getBeforeMethodList();
			beforeMethod = beforeMethodList.get(beforeMethodList.size() - 1);
		}

		// adiciona os novos statements ao final do método
		// beforeMethod.addStatements(commomStatements, -1);

		createClassVariable();

		// Remove statements from methods.
		for (TestMethod testMethod : targetSuite.getTestMethodList()) {
			testMethod.removeStatements(0, commomStatements.size());
		}

		includeSetupStatements(beforeMethod);
	}

	private void createClassVariable() {
		if (commomStatements != null) {
			for (TestStatement item : commomStatements) {
				if (item.getCodeElement() instanceof VariableDeclarationStatement) {
					VariableDeclarationStatement variableDeclaration = (VariableDeclarationStatement) item.getCodeElement();
					// declara variavel no contexto de classe.
					targetSuite.createNewFixture(variableDeclaration.getVariableType(), variableDeclaration.getVariableName());
				}
			}
		}
	}

	private void includeSetupStatements(TestMethod beforeMethod) {
		if (commomStatements != null) {
			List<TestStatement> setupStatements = new ArrayList<TestStatement>();
			for (TestStatement item : commomStatements) {
				if (item.getCodeElement() instanceof VariableDeclarationStatement) {
					VariableDeclarationStatement variableDeclaration = (VariableDeclarationStatement) item.getCodeElement();
					// declara variavel no contexto de classe.
					if (variableDeclaration.getInitialization() != null) {
						variableDeclaration.transformInExpression();
						setupStatements.add(item);
					}
				} else {
					setupStatements.add(item);
				}
			}
			beforeMethod.addStatements(setupStatements, -1);
		}
	}
}
