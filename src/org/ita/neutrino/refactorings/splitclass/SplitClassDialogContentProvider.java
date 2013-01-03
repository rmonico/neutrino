package org.ita.neutrino.refactorings.splitclass;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;

public class SplitClassDialogContentProvider implements
		IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object arg0) {
		return ((ArrayList<TestMethod>) arg0).toArray();
	}

}
