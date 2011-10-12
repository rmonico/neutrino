package org.ita.neutrino.inputfinalization;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.TestMethod;
import org.ita.neutrino.abstracttestparser.TestStatement;
import org.ita.neutrino.abstracttestparser.TestSuite;
import org.ita.neutrino.codeparser.Annotation;
import org.ita.neutrino.extractmethod.AbstractExtractMethodRefactoring;
import org.ita.neutrino.junit4parser.JUnitTestMethod;

public class InputFinalizationRefactoring extends AbstractExtractMethodRefactoring {

	private JUnitTestMethod targetMethod;

	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if ((!(getTargetFragment() instanceof org.ita.neutrino.junit4parser.JUnitTestMethod)) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a finalization test method.");
		} else {
			targetMethod = (JUnitTestMethod) getTargetFragment();
			if (!(hasAfterAnnotation(targetMethod))) {
				problems.add("Selection must be a variable declaration.");
			}
		}

		if (problems != null && problems.size() > 0) {
			problems.add("Note: Select a finalization method, press input finalization, the method will be copied to the end of each test method, the finalization method will be removed.");
		}

		return problems;
	}

	public boolean hasAfterAnnotation(JUnitTestMethod targetMethod) {
		for (Annotation item : targetMethod.getCodeElement().getAnnotations()) {
			if (item.getQualifiedName().equals("org.junit.After")) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		TestSuite ts = targetMethod.getParent();

		for (TestMethod tm : targetMethod.getParent().getAllTestMethodList()) {
			if (isTestMethod(tm, ts)) {
				List<TestStatement> finalizationStatements = new ArrayList<TestStatement>();
				finalizationStatements.addAll(targetMethod.getStatements());

				tm.addStatements(finalizationStatements, tm.getStatements().size());
			}
		}

		ts.removeTestMethods(ts.getAllTestMethodList().indexOf(targetMethod), 1);
	}

	private boolean isTestMethod(TestMethod tm, TestSuite ts) {
		return ts.getTestMethodList().indexOf(tm) > -1;
	}

}
