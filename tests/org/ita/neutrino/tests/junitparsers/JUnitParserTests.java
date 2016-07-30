package org.ita.neutrino.tests.junitparsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.astparser.ASTParser;
import org.ita.neutrino.tests.PluginAbstractTests;
import org.ita.neutrino.tparsers.abstracttestparser.TestParserException;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitAction;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitAssertion;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitGenericParser;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitTestBattery;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitTestMethod;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitTestStatement;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitTestSuite;
import org.junit.Test;

public abstract class JUnitParserTests extends PluginAbstractTests {

	protected ASTParser codeParser;
	protected JUnitTestBattery battery;
	protected JUnitTestSuite suite;
	protected JUnitTestMethod testNothing0;
	protected List<? extends JUnitTestStatement> statementList;

	protected abstract void prepareTests() throws JavaModelException, ParserException;
	protected abstract JUnitGenericParser instantiateJUnitParser();

	@Test
	public void testTestParser() throws TestParserException, ParserException, JavaModelException {
		prepareTests(); 
		
		JUnitGenericParser testParser = instantiateJUnitParser();
		
		testParser.setEnvironment(codeParser.getEnvironment());
		
		testParser.parse();
		
		battery = testParser.getBattery();
		
		testBatteryParser();
		
		suite = battery.getSuiteByName("MockClass");
		
		testSuiteParser();

		testSuiteFixtureParser();
		
		testSuiteMethodParser();
		
		testBlockElementsParser();
	}
	
	private void testBatteryParser() {
		assertNull("Bateria de testes: Parent", battery.getParent());
		assertEquals("Bateria de testes: Size of suite list", 1, battery.getSuiteList().size());
	}
	
	protected abstract void testSuiteParser();

	private void testSuiteFixtureParser() {
		assertEquals("Suite: fixture list (size)", 2, suite.getFixtures().size());
		assertEquals("Suite: fixture 0", "fixture0", suite.getFixtures().get(0).getName());
		assertEquals("Suite: fixture 1", "fixture1", suite.getFixtures().get(1).getName());
	}

	private void testSuiteMethodParser() {
		assertEquals("Suite: before method list (size)", 1, suite.getBeforeMethodList().size());
		assertEquals("Suite: before method 0", "setUp", suite.getBeforeMethodList().get(0).getName());
		
		
		assertEquals("Suite: after method list (size)", 1, suite.getAfterMethodList().size());
		assertEquals("Suite: after method 0", "tearDown", suite.getAfterMethodList().get(0).getName());
		
		
		assertEquals("Suite: test method list (size)", 2, suite.getTestMethodList().size());
		
		assertEquals("Suite: test method 0", "testNothing0", suite.getTestMethodList().get(1).getName());
		assertEquals("Suite: test method 1", "testNothing1", suite.getTestMethodList().get(0).getName());
	}

	protected abstract Class<? extends JUnitAction> getActionClass();

	protected abstract Class<? extends JUnitAssertion> getAssertionClass();

	private void testBlockElementsParser() {
		testNothing0 = suite.getMethodByName("testNothing0");
		
		statementList = testNothing0.getStatements();
		
		assertEquals("StatementList: size", 2, statementList.size());
		
		
		assertEquals("Action: classe", statementList.get(0).getClass(), getActionClass());
		
		JUnitAction action = (JUnitAction) statementList.get(0);
		
		assertEquals("Action: valor", "action();\n", action.toString());
		
		this.testAssertions();
		
	}
	
	protected abstract void testAssertions();
	
}
