package org.ita.neutrino.testfiles.junit3parsertests;

import junit.framework.TestCase;

public class MockClass extends TestCase {

	@SuppressWarnings("unused")
	private Object fixture0 = new Object();
	@SuppressWarnings("unused")
	private Object fixture1 = new Object();
	
	public void setUp() {
		action();
	}
	
	public void testNothing0() {
		action();
		
		assertTrue("Comment", true);
	}


	public void testNothing1() {
		action();
		
		assertTrue("Comment", true);
	}

	
	private void action() {
		
	}
	
	public void tearDown() {
	}
}
