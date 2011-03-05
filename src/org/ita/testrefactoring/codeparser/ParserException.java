package org.ita.testrefactoring.codeparser;


public class ParserException extends Exception {

	public ParserException(Exception e) {
		super(e);
	}

	public ParserException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5652462744637344877L;

}
