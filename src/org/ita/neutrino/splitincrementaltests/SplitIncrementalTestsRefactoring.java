package org.ita.neutrino.splitincrementaltests;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.Assertion;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;

public class SplitIncrementalTestsRefactoring extends AbstractRefactoring {
	private TestMethod targetMethod;
	private int index = 1;

	@Override
	public List<String> checkInitialConditions() {

		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a method.");
		} else {
			targetMethod = (TestMethod) getTargetFragment();
		}

		if (problems != null && problems.size() > 0) {
			problems.add("Note: Select the bigger method wich groups smaller ones, press group incremental tests, all asserts will be brought to the selected method, the smaller methods will be removed.");
		}
		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		List<TestStatement> commomStatements = new ArrayList<TestStatement>();
		List<TestStatement> temp = new ArrayList<TestStatement>();
		commomStatements.addAll(targetMethod.getStatements());
		List<Integer> asserts = new ArrayList<Integer>();
		boolean lastWasAssert = false;

		for (int i = 0; i < commomStatements.size(); i++) {
			if (commomStatements.get(i) instanceof Assertion) {
				asserts.add(i);
				lastWasAssert = true;
			} else {
				if (lastWasAssert) {
					createMethod(temp);
				}
				lastWasAssert = false;
			}
			temp.add(commomStatements.get(i));
		}
		if (lastWasAssert) {
			createMethod(commomStatements);
		}
	}

	private void createMethod(List<TestStatement> statements) {
		boolean canRemove = false;
		for (int i = statements.size() - 1; i >= 0; i--) {
			if (statements.get(i) instanceof Assertion) {
				if (canRemove) {
					statements.remove(i);
				}
			} else {
				canRemove = true;
			}
		}

		List<TestStatement> copied = new ArrayList<TestStatement>();
		copied.addAll(statements);

		String newMethodName = null;
		List<? extends TestMethod> methods = targetMethod.getParent().getAllTestMethodList();
		for (int i = index; i < Integer.MAX_VALUE;) {
			index++;
			newMethodName = targetMethod.getName() + i;
			for (TestMethod tm : methods) {
				if (tm.getName().equals(newMethodName)) {
					continue;
				}
			}
			break;
		}

		TestMethod tm = targetMethod.getParent().createNewTestMethod(newMethodName);
		tm.addStatements(copied, 0);
	}
}
