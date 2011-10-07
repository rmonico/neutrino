package org.ita.neutrino.groupincrementaltests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.astparser.ASTMethodInvocationStatement;
import org.ita.neutrino.junitgenericparser.JUnitAssertion;

public class GroupIncrementalTestsRefactoring extends AbstractRefactoring {
	private TestMethod targetMethod;
	private List<TestMethod> commomTestMethod;

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof TestMethod)) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a method.");
		} else {
			targetMethod = (TestMethod) getTargetFragment();

			commomTestMethod = getCommomTestMethod();
			if (commomTestMethod.size() < 1) {
				problems.add("There is no common interaction tests");
			}
		}

		if (problems != null && problems.size() > 0) {
			problems.add("Note: Select the bigger method wich groups smaller ones, press group incremental tests, all asserts will be brought to the selected method, the smaller methods will be removed.");
		}
		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		if (commomTestMethod != null && commomTestMethod.size() > 0) {
			groupAsserts();

			TestSuite ts = targetMethod.getParent();

			// remove inner tests.
			for (TestMethod tm : commomTestMethod) {
				int index = targetMethod.getParent().getTestMethodList().indexOf(tm);
				ts.removeTestMethods(index, 1);
			}
		}
	}

	private void groupAsserts() {
		int originalCursor = 0;
		int modifiedCursor = 0;
		for (TestMethod tm : commomTestMethod) {
			List<TestStatement> temp = new ArrayList<TestStatement>();
			temp.addAll(tm.getStatements());

			for (int i = originalCursor; i < temp.size(); i++) {
				if (temp.get(i).equals(targetMethod.getStatements().get(i))) {
					// Statements already in there.
					originalCursor++;
				} else {
					// Insert Asserts in the right place.
					List<TestStatement> insert = new ArrayList<TestStatement>();
					insert.add(temp.get(i));
					targetMethod.addStatements(insert, modifiedCursor);
				}
				modifiedCursor++;
			}
		}
	}

	private List<TestMethod> getCommomTestMethod() {
		List<TestMethod> commomTestMethod = new ArrayList<TestMethod>();
		List<TestMethod> sortedMethods = new ArrayList<TestMethod>();
		List<TestStatement> commomStatements = new ArrayList<TestStatement>();

		commomStatements.addAll(targetMethod.getStatements());
		sortedMethods.addAll(targetMethod.getParent().getTestMethodList());

		Collections.sort(sortedMethods);
		for (TestMethod tm : sortedMethods) {
			if (targetMethod.getStatements().size() < tm.getStatements().size()) {
				break;
			}
			if (!tm.getName().equals(targetMethod.getName())) {
				List<TestStatement> temp = new ArrayList<TestStatement>();
				temp.addAll(tm.getStatements());
				boolean reachAssert = false;
				boolean isCommom = true;
				for (int i = 0; i < temp.size(); i++) {
					if (!temp.get(i).equals(commomStatements.get(i))) {
						if (temp.get(i) instanceof JUnitAssertion || (temp.get(i) instanceof ASTMethodInvocationStatement && ((ASTMethodInvocationStatement) temp.get(i)).isAssert())) {
							reachAssert = true;
						} else {
							isCommom = false;
							break;
						}
					} else if (reachAssert) {
						isCommom = false;
						break;
					}
				}
				if (isCommom) {
					commomTestMethod.add(tm);
				}
			}
		}
		return commomTestMethod;
	}
}
