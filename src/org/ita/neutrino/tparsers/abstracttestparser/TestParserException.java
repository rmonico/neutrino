package org.ita.neutrino.tparsers.abstracttestparser;


public class TestParserException extends Exception {

	public TestParserException(Exception e) {
		super(e);
	}
	
	public TestParserException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3214297279812540130L;

}
