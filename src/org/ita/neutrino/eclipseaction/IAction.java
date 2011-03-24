package org.ita.neutrino.eclipseaction;

import org.eclipse.jface.viewers.ISelection;

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
}
