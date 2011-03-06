package org.ita.testrefactoring.testfiles.junitparsertests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MockClass {

	private Object fixture0 = new Object();
	private Object fixture1 = new Object();
	
	@Before
	public void setup() {
		action();
	}
	
	@Test
	public void testNothing0() {
		action();
		
		assertTrue("Comment", true);
	}


	@Test
	public void testNothing1() {
		action();
		
		assertTrue("Comment", true);
	}

	
	private void action() {
		
	}
	
	@After
	public void teardown() {
		
	}
}
