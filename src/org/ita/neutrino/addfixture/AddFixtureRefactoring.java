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
			List<? extends TestStatement> smallerList;
			if (testMethod.getStatements().size() > 0) {
				smallerList = testMethod.getStatements();

				if (testMethod.getName().equals(metodoCorrente)) {
					// replace on variable declacation statement to expressionStatement.
					for (int j = 0; j < smallerList.size(); j++) {
						List<TestStatement> novoStatement = new ArrayList<TestStatement>();

						// TODO: Declaração de variável não é statement, isso gera erro.
						novoStatement.add(getTestStatementWithNoDeclaration(this.targetAction, null));
						if (smallerList.get(j).equals(this.targetAction)) {
							testMethod.removeStatements(j, 1);
							testMethod.addStatements(novoStatement, j);
							break;
						}
					}

				} else {
					// Replace on the variable declaration with same type and name.
					for (int j = 0; j < smallerList.size(); j++) {
						if (smallerList.get(j).getCodeElement() instanceof VariableDeclarationStatement) {
							VariableDeclarationStatement var = (VariableDeclarationStatement) smallerList.get(j).getCodeElement();
							if (var.getVariableType().equals(targetVar.getVariableType()) && var.getVariableName().equals(targetVar.getVariableName())) {

								List<TestStatement> novoStatement = new ArrayList<TestStatement>();
								novoStatement.add(getTestStatementWithNoDeclaration(smallerList.get(j), targetVar.getVariableName()));
								testMethod.removeStatements(j, 1);
								testMethod.addStatements(novoStatement, j);

								if (var.getInitialization() == null) {
									// TODO: remove the line.
								}
								break;
							}
						}
					}
				}
			}
		}
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
