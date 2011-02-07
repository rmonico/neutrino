package org.ita.testrefactoring.addexplanation;

import org.eclipse.jface.viewers.ISelection;
import org.ita.testrefactoring.eclipseaction.ActionException;
import org.ita.testrefactoring.eclipseaction.IAction;

public class AddExplanationAction implements IAction {

	@Override
	public ISelection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelection(ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() throws ActionException {
		System.out.println("Running ==> org.ita.testrefactoring.addexplanation.AddExplanationAction");
	}

}
