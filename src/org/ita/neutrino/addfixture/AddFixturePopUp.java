package org.ita.neutrino.addfixture;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class AddFixturePopUp  implements IObjectActionDelegate{


	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		// TODO Auto-generated method stub
		MessageDialog.openInformation(null, "title", "setActivePart");
	}

	@Override
	public void run(IAction arg0) {
		// TODO Auto-generated method stub
		MessageDialog.openInformation(null, "title", "run");
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		// TODO Auto-generated method stub
		MessageDialog.openInformation(null, "title", "selectionChanged");
	}

}
