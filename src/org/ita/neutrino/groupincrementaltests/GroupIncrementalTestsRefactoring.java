package org.ita.neutrino.groupincrementaltests;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.Action;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.codeparser.Statement;
import org.ita.neutrino.codeparser.VariableDeclarationStatement;

public class GroupIncrementalTestsRefactoring extends AbstractRefactoring {
	private Action targetAction;
	
	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a method.");
		} else {
			targetAction = (Action) getTargetFragment();
			
			Statement statement = targetAction.getCodeElement();
			if (!(statement instanceof VariableDeclarationStatement)) {
				problems.add("Selection must be a method.");
			} else {
				
			}
		}

		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		// TODO Auto-generated method stub
		//TestMethod tm = targetAction.getParent();
		//TestSuite ts = tm.getParent();
	}

}
