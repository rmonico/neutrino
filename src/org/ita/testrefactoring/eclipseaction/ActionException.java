package org.ita.testrefactoring.eclipseaction;

public class ActionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4720934819220562698L;
	
	public ActionException(Exception e) {
		super(e);
	}

}
