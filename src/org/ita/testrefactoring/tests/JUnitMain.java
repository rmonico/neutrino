package org.ita.testrefactoring.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.ita.testrefactoring.eclipseaction.ActionException;
import org.ita.testrefactoring.eclipseaction.IAction;
import org.ita.testrefactoring.tests.addexplanation.AddExplanationRefactoringTests;
import org.ita.testrefactoring.tests.astparser.TestPackageParsing;
import org.ita.testrefactoring.tests.astparser.TestSupportedBlockSintax;
import org.ita.testrefactoring.tests.junitparser.JUnitParserTests;
import org.junit.runner.JUnitCore;
import org.zero.utils.tests.JUnitUtilsTests;
import org.zero.utils.tests.ListenerTests;
import org.zero.utils.tests.StringUtilsTests;

public class JUnitMain implements IAction {
	
	@Override
	public ISelection getSelection() {
		return null;
	}

	@Override
	public void setSelection(ISelection selection) {
	}

	@Override
	public void run() throws ActionException {
		JUnitCore core = new JUnitCore();
		
		ConsoleListener listener = new ConsoleListener();
		
		core.addListener(listener);
		
		Class<?>[] classesToTest = getClassesToTest();
		
		core.run(classesToTest);
		
		if (listener.isAllTestsOk()) {
			MessageDialog.openInformation(null, "JUnit Tests", listener.getResultsString());
		} else {
			MessageDialog.openWarning(null, "JUnit Tests", listener.getResultsString());
		}

	}

	private Class<?>[] getClassesToTest() {
		List<Class<?>> classesToTest = new ArrayList<Class<?>>();
		
//		Demo de como fazer JUnit para plugin no Eclipse
//		classesToTest.add(ASTRewriteExample.class);
		
//		Testes de org.zero.utils
		classesToTest.add(JUnitUtilsTests.class);
		classesToTest.add(StringUtilsTests.class);
		classesToTest.add(ListenerTests.class);
		
		classesToTest.add(TestSupportedBlockSintax.class);
		classesToTest.add(TestPackageParsing.class);
		classesToTest.add(JUnitParserTests.class);
		classesToTest.add(AddExplanationRefactoringTests.class);
//		classesToTest.add(ExtractInitializationMethodTests.class);
//		classesToTest.add(CreateModelTestTests.class);
		
		Class<?>[] result = new Class<?>[classesToTest.size()];
		
		classesToTest.toArray(result);

		return result;
	}

}
