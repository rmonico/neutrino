package org.ita.neutrino.testfiles.junit5parsertests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MockClass {

	@SuppressWarnings("unused")
	private Object fixture0 = new Object();
	@SuppressWarnings("unused")
	private Object fixture1 = new Object();
	
	@BeforeAll
	public void setUp() {
		action();
	}
	
	@Test
	public void testNothing0() {
		action();
		
		assertTrue(true, "Comment");
	}


	@Test
	public void testNothing1() {
		action();
		
		assertTrue(true, "Comment");
	}

	
	private void action() {
		
	}
	
	@AfterAll
	public void tearDown() {
		
	}
}
