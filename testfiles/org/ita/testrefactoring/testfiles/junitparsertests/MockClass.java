package org.ita.testrefactoring.testfiles.junitparsertests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MockClass {

	@Test
	public void testNothing() {
		action();
		
		assertTrue("Comment", true);
	}

	private void action() {
		// TODO Auto-generated method stub
		
	}
}
