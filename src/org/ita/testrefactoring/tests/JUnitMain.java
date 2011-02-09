package org.ita.testrefactoring.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.ita.testrefactoring.eclipseaction.ActionException;
import org.ita.testrefactoring.eclipseaction.IAction;
import org.junit.runner.JUnitCore;

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
		
//		classesToTest.add(ASTRewriteExample.class);
		classesToTest.add(ASTParserTests.class);
//		classesToTest.add(AddExplanationRefactoringTests.class);
//		classesToTest.add(ExtractInitializationMethodTests.class);
//		classesToTest.add(CreateModelTestTests.class);
		
		Class<?>[] result = new Class<?>[classesToTest.size()];
		
		classesToTest.toArray(result);

		return result;
	}

}
