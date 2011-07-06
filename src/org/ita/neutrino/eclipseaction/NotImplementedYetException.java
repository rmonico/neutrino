package org.ita.neutrino.eclipseaction;

import org.eclipse.jface.dialogs.MessageDialog;

public class NotImplementedYetException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7897959458354894021L;
	
	
	public NotImplementedYetException() {
		super();

		MessageDialog.openWarning(null, "Not implemented yet...", "Not implemented yet...");
	}

}
