package org.ita.neutrino.refactorings.splitclass;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.ita.neutrino.refactorings.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.refactorings.abstracrefactoring.RefactoringException;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;
import org.ita.neutrino.tparsers.abstracttestparser.TestSuite;

public class SplitClassRefactoring extends AbstractRefactoring {
	TestSuite targetSuite;
	List<TestMethod> targetMethods;
	
	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();

		if (!(getTargetFragment() instanceof TestSuite) || (getTargetFragment() == null)) {
			problems.add("Selection is not valid. Select a test suite.");
		}
		targetSuite = (TestSuite) getTargetFragment();
	
		
		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		List<TestMethod> methodList = new ArrayList<TestMethod>();
		methodList.addAll(targetSuite.getAllTestMethodList());
		
		ListSelectionDialog dlg = new ListSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				methodList, 
			    new SplitClassDialogContentProvider(),
			    new SplitClassDialogLabelProvider(),
			    "Select the test methods to move to a super class.");
				
		if(dlg.open() == ListSelectionDialog.CANCEL) {
			List<String> lst = new ArrayList<String>();
			lst.add("Cancel pressed.");
			throw new RefactoringException(lst);
		}
		
		targetMethods = new ArrayList<TestMethod>();
		
		for(int i = 0; i < dlg.getResult().length; i++)
			targetMethods.add((TestMethod) dlg.getResult()[i]);
		
		targetSuite.getParent().createNewSuite("testSuite", "testpack");
	}

}
