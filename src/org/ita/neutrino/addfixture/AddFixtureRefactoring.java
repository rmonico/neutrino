package org.ita.neutrino.addfixture;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.Action;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestSuite;
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
		TestMethod tm =  targetAction.getParent();
		TestSuite ts = tm.getParent();
		
		ts.createNewFixture(variableDeclaration.getVariableType(), variableDeclaration.getVariableName());
	}

}
