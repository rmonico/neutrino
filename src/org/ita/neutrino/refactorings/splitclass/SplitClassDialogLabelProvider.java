package org.ita.neutrino.refactorings.splitclass;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.ita.neutrino.tparsers.abstracttestparser.TestMethod;

public class SplitClassDialogLabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getImage(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(Object arg0) {
		return ((TestMethod) arg0).getName();
	}

}
