package org.ita.neutrino.refactorings.addfixture;

import java.util.ArrayList;
import java.util.List;

import javax.activity.InvalidActivityException;

import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.refactorings.abstracrefactoring.RefactoringException;
import org.ita.neutrino.tparsers.abstracttestparser.Action;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestStatement;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class AddFixtureRefactoring extends AbstractRefactoring {

	// private VariableDeclarationStatement variableDeclaration;
	private Action targetAction;

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof Action)) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a variable.");
		} else {
			targetAction = (Action) getTargetFragment();
			if (!targetAction.isVariableDeclarationStatement()) {
				problems.add("Selection must be a variable declation.");
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
		try {
			ts.createNewFixture(targetAction);
		} catch (InvalidActivityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String metodoCorrente = this.targetAction.getParent().getName();
		TestSuite suite = this.targetAction.getParent().getParent();
		List<? extends TestMethod> testMethods = suite.getTestMethodList();

		for (TestMethod testMethod : testMethods) {
			if (testMethod.getStatements().size() > 0) {
				if (testMethod.getName().equals(metodoCorrente)) {
					// replace on variable declacation statement to expressionStatement.
					for (TestStatement item : testMethod.getStatements()) {
						if (item.equals(this.targetAction)) {
							item.transformInExpression();
							break;
						}
					}
				} else {
					// Replace on the variable declaration with same type and name.
					for (TestStatement item : testMethod.getStatements()) {
						if (item.getCodeElement().isVariableDeclaration() && item.getCodeElement().similarDeclaration(targetAction)) {
							item.transformInExpression(targetAction);
							break;
						}
					}
				}
			}
		}
	}

}
