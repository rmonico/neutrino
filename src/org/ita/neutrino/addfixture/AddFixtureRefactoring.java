package org.ita.neutrino.addfixture;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.Action;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.astparser.ASTVariableDeclarationStatement;
import org.ita.neutrino.codeparser.Statement;
import org.ita.neutrino.codeparser.VariableDeclarationStatement;

public class AddFixtureRefactoring extends AbstractRefactoring {

	private VariableDeclarationStatement variableDeclaration;
	private Action targetAction;

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof Action)) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a variable.");
		} else {
			targetAction = (Action) getTargetFragment();
			Statement statement = targetAction.getCodeElement();
			if (!(statement instanceof VariableDeclarationStatement)) {
				problems.add("Selection must be a variable declation.");
			} else {
				variableDeclaration = (VariableDeclarationStatement) statement;
			}
		}

		if (problems != null && problems.size() > 0) {
			problems.add("Note: Select a variable declaration, press add fixture, the fixture will work for every test methods in class with the same declaration.");
		}

		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		TestMethod tm = targetAction.getParent();
		TestSuite ts = tm.getParent();

		// declara variavel no contexto de classe.
		ts.createNewFixture(variableDeclaration.getVariableType(), variableDeclaration.getVariableName());

		String metodoCorrente = this.targetAction.getParent().getName();
		TestSuite suite = this.targetAction.getParent().getParent();
		List<? extends TestMethod> testMethods = suite.getTestMethodList();
		VariableDeclarationStatement targetVar = (VariableDeclarationStatement) this.targetAction.getCodeElement();

		for (int i = 0; i < testMethods.size(); i++) {
			TestMethod testMethod = testMethods.get(i);
			List<? extends TestStatement> statementList;
			if (testMethod.getStatements().size() > 0) {
				statementList = testMethod.getStatements();

				if (testMethod.getName().equals(metodoCorrente)) {
					// replace on variable declacation statement to expressionStatement.
					for (int j = 0; j < statementList.size(); j++) {
						if (statementList.get(j).equals(this.targetAction)) {
							VariableDeclarationStatement var = (VariableDeclarationStatement) this.targetAction.getCodeElement();
							changeStatements(var, statementList, testMethod, j, targetVar.getVariableName());
							break;
						}
					}

				} else {
					// Replace on the variable declaration with same type and name.
					for (int j = 0; j < statementList.size(); j++) {
						if (statementList.get(j).getCodeElement() instanceof VariableDeclarationStatement) {
							VariableDeclarationStatement var = (VariableDeclarationStatement) statementList.get(j).getCodeElement();
							if (var.getVariableType().equals(targetVar.getVariableType()) && var.getVariableName().equals(targetVar.getVariableName())) {
								changeStatements(var, statementList, testMethod, j, targetVar.getVariableName());
								break;
							}
						}
					}
				}
			}
		}
	}

	private void changeStatements(VariableDeclarationStatement var, List<? extends TestStatement> statementList, TestMethod testMethod, int statementIndex, String variableName) {
		List<TestStatement> novoStatement = new ArrayList<TestStatement>();
		if (var.getInitialization() != null) {
			novoStatement.add(getTestStatementWithNoDeclaration(statementList.get(statementIndex), variableName));
		}

		testMethod.removeStatements(statementIndex, 1);
		testMethod.addStatements(novoStatement, statementIndex);
	}

	private TestStatement getTestStatementWithNoDeclaration(TestStatement from, String variableName) {
		ASTVariableDeclarationStatement element = (ASTVariableDeclarationStatement) from.getCodeElement();
		if (variableName == null) {
			element.transformInExpression();
		} else {
			element.transformInExpression(variableName);
		}
		return from;
	}
}
