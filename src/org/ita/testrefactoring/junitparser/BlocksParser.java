package org.ita.testrefactoring.junitparser;

import java.util.ArrayList;
import java.util.List;

import org.ita.testrefactoring.codeparser.Block;
import org.ita.testrefactoring.codeparser.Method;
import org.ita.testrefactoring.codeparser.MethodInvocation;
import org.ita.testrefactoring.codeparser.Statement;
import org.ita.testrefactoring.codeparser.Type;

class BlocksParser {

	private JUnitTestBattery battery;
	private List<Block> allBlocks;

	public void setBattery(JUnitTestBattery battery) {
		this.battery = battery;
	}

	public void parse() {
		locateAllBlocks();

		for (Block block : allBlocks) {
			parseBlock(block);
		}
	}

	private void locateAllBlocks() {
		allBlocks = new ArrayList<Block>();

		for (JUnitTestSuite suite : battery.getSuiteList()) {
			// cp = codeParser
			for (JUnitTestMethod tpBeforeMethod : suite.getBeforeMethodList()) {
				Method cpBeforeMethod = tpBeforeMethod.getCodeElement();

				if (!cpBeforeMethod.getNonAccessModifier().isAbstract()) {
					allBlocks.add(cpBeforeMethod.getBody());
				}
			}

			// tp = testParser
			for (JUnitTestMethod tpTestMethod : suite.getTestMethodList()) {
				Method cpTestMethod = tpTestMethod.getCodeElement();

				if (cpTestMethod.getNonAccessModifier().isAbstract()) {
					allBlocks.add(cpTestMethod.getBody());
				}
			}

			for (JUnitTestMethod tpAfterMethod : suite.getAfterMethodList()) {
				Method cpAfterMethod = tpAfterMethod.getCodeElement();

				if (!cpAfterMethod.getNonAccessModifier().isAbstract()) {
					allBlocks.add(cpAfterMethod.getBody());
				}

			}
		}

	}

	private void parseBlock(Block block) {
		for (Statement statement : block.getStatementList()) {
			parseStatement(statement);
		}
	}

	private void parseStatement(Statement statement) {
		if (statement instanceof MethodInvocation) {
			MethodInvocation methodInvocation = (MethodInvocation) statement;

			if (isAssertion(methodInvocation)) {

			}
		}
	}

	private boolean isAssertion(MethodInvocation methodInvocation) {
		
		Type methodType = methodInvocation.getCalledMethod().getParent();
		String fullName = methodType.getQualifiedName() + "." + methodInvocation.getCalledMethod().getName();

		if (fullName.equals("org.junit.Assert.assertArrayEquals()")
				|| fullName.equals("org.junit.Assert.assertEquals()")
				|| fullName.equals("org.junit.Assert.assertTrue()")
				|| fullName.equals("org.junit.Assert.assertFalse()")
				|| fullName.equals("org.junit.Assert.assertNull()")
				|| fullName.equals("org.junit.Assert.assertNotNull()")
				|| fullName.equals("org.junit.Assert.assertSame()")
				|| fullName.equals("org.junit.Assert.assertNotSame()")
				|| fullName.equals("org.junit.Assert.assertThat()")

				|| fullName.equals("junit.framework.Assert.assertEquals()")
				|| fullName.equals("junit.framework.Assert.assertTrue()")
				|| fullName.equals("junit.framework.Assert.assertFalse()")
				|| fullName.equals("junit.framework.Assert.assertNull()")
				|| fullName.equals("junit.framework.Assert.assertNotNull()")
				|| fullName.equals("junit.framework.Assert.assertSame()")
				|| fullName.equals("junit.framework.Assert.assertNotSame()")) {
			return true;
		} else {
			return false;
		}
	}

}
