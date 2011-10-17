package org.ita.neutrino.testsmells.core;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class IDEUtils {
	public static ISelection ensureMarkerSelected(IMarker marker) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();			
		
		try {
			IDE.openEditor(page, marker);
		} catch (PartInitException e) {
			throw new RuntimeException("Could not open file", e);
		}
		
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
	}
}
