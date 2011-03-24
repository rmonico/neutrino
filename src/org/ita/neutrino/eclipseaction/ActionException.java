package org.ita.neutrino.eclipseaction;

public class ActionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4720934819220562698L;
	
	public ActionException(Exception e) {
		super(e);
	}

	public ActionException(String message) {
		super(message);
	}

}
