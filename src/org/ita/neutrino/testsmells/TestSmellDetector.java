package org.ita.neutrino.testsmells;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class TestSmellDetector {
	public void run(IProject project, IResourceDelta delta, IProgressMonitor progressMonitor) {
		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault();
		}
		display.asyncExec(new Runnable() {			
			@Override
			public void run() {
				MessageBox messageBox = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_INFORMATION);
				messageBox.setMessage("Smell code detection will go in here");
				messageBox.open();
			}
		});		
	}
}
