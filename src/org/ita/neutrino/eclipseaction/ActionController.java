package org.ita.neutrino.eclipseaction;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class ActionController implements IWorkbenchWindowActionDelegate {
	
	private ISelection selection;

	@Override
	public void run(IAction act) {
		String iActionName = act.getId();
		
		System.out.println("Ação id: \"" + iActionName + "\" disparada.");
		
		Object action = null;
		try {
			 action = Class.forName(iActionName).newInstance();
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Classe não encontrada: \"" + iActionName + "\"");
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("Erro instanciando a classe \"" + iActionName + "\"", e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Erro instanciando a classe \"" + iActionName + "\"", e);
		}
		
		if (!(action instanceof org.ita.neutrino.eclipseaction.IAction)) {
			throw new IllegalArgumentException("\"" + iActionName + "\" deve ser implementar org.ita.neutrino.old.abstractrefactoring.IAction.");
		}
		
		org.ita.neutrino.eclipseaction.IAction iAction = (org.ita.neutrino.eclipseaction.IAction) action;
			
		iAction.setSelection(selection);
		
		try {
			iAction.run();
		} catch (ActionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow arg0) {
	}

}
