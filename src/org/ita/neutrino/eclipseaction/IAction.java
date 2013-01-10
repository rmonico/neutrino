package org.ita.neutrino.eclipseaction;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;

public interface IAction {
	
	/**
	 * Deve devolver o objeto ISelection associado com a ação.
	 * 
	 * @return
	 */
	public ISelection getSelection();
	
	public void setSelection(ISelection selection);

	/**
	 * Chamado quando a ação é executada
	 */
	public void run() throws ActionException;
	
	public IWorkbenchWindow getWindow();
	
	public void setWindow(IWorkbenchWindow window);
}
