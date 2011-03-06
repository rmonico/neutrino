package org.ita.testrefactoring.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.testrefactoring.abstracttestparser.TestParserException;
import org.ita.testrefactoring.astparser.ASTParser;
import org.ita.testrefactoring.codeparser.CodeElement;
import org.ita.testrefactoring.codeparser.ParserException;
import org.ita.testrefactoring.junitparser.JUnitParser;
import org.ita.testrefactoring.junitparser.JUnitTestBattery;
import org.ita.testrefactoring.junitparser.JUnitTestSuite;
import org.junit.Before;
import org.junit.Test;

public class JUnitParserTests extends RefactoringAbstractTests {
	
	@Before
	public void setup() {
		// Não apaga o projeto de testes após rodar cada teste.
		setAlwaysDeleteTestProject(true);
	}
	
	@Test
	public void testSomething() throws TestParserException, ParserException, JavaModelException {
		StringBuilder mockClassCode = new StringBuilder();

		mockClassCode.append("package org.ita.testrefactoring.testfiles.junitparsertests;\n");
		mockClassCode.append("\n");
		mockClassCode.append("import static org.junit.Assert.assertTrue;\n");
		mockClassCode.append("\n");
		mockClassCode.append("import org.junit.After;\n");
		mockClassCode.append("import org.junit.Before;\n");
		mockClassCode.append("import org.junit.Test;\n");
		mockClassCode.append("\n");
		mockClassCode.append("public class MockClass {\n");
		mockClassCode.append("\n");
		mockClassCode.append("    private Object fixture0 = new Object();\n");
		mockClassCode.append("    private Object fixture1 = new Object();\n");
		mockClassCode.append("    \n");
		mockClassCode.append("    @Before\n");
		mockClassCode.append("    public void setup() {\n");
		mockClassCode.append("        action();\n");
		mockClassCode.append("    }\n");
		mockClassCode.append("    \n");
		mockClassCode.append("    @Test\n");
		mockClassCode.append("    public void testNothing0() {\n");
		mockClassCode.append("        action();\n");
		mockClassCode.append("        \n");
		mockClassCode.append("        assertTrue(\"Comment\", true);\n");
		mockClassCode.append("    }\n");
		mockClassCode.append("\n");
		mockClassCode.append("\n");
		mockClassCode.append("    @Test\n");
		mockClassCode.append("    public void testNothing1() {\n");
		mockClassCode.append("        action();\n");
		mockClassCode.append("        \n");
		mockClassCode.append("        assertTrue(\"Comment\", true);\n");
		mockClassCode.append("    }\n");
		mockClassCode.append("\n");
		mockClassCode.append("    \n");
		mockClassCode.append("    private void action() {\n");
		mockClassCode.append("        \n");
		mockClassCode.append("    }\n");
		mockClassCode.append("    \n");
		mockClassCode.append("    @After\n");
		mockClassCode.append("    public void teardown() {\n");
		mockClassCode.append("        \n");
		mockClassCode.append("    }\n");
		mockClassCode.append("}\n");

		ICompilationUnit mockCompilationUnit = createSourceFile("org.ita.testrefactoring.testfiles.junitparsertests", "MockClass.java", mockClassCode); 
		
		
		ASTParser codeParser = new ASTParser();
		
		codeParser.setActiveCompilationUnit(mockCompilationUnit);
		codeParser.setCompilationUnits(new ICompilationUnit[] {mockCompilationUnit});
		
		codeParser.parse();
		
		
		JUnitParser testParser = new JUnitParser();
		
		testParser.setEnvironment(codeParser.getEnvironment());
		
		testParser.parse();

		JUnitTestBattery battery = testParser.getBattery();
		
		assertNull("Bateria de testes: Parent", battery.getParent());
		assertEquals("Bateria de testes: Size of suite list", 1, battery.getSuiteList().size());
		
		JUnitTestSuite suite = battery.getSuiteList().get(0);
		
		assertEquals("Suite: parent", battery, suite.getParent());
		
		CodeElement expectedSuiteCodeElement = codeParser.getEnvironment().getTypeCache().get("org.ita.testrefactoring.testfiles.junitparsertests.MockClass");
		
		assertEquals("Suite: code element", expectedSuiteCodeElement, suite.getCodeElement());

		assertEquals("Suite: fixture list (size)", 2, suite.getFixtures().size());
		assertEquals("Suite: fixture 0", "fixture0", suite.getFixtures().get(0).getName());
		assertEquals("Suite: fixture 1", "fixture1", suite.getFixtures().get(1).getName());
		
		assertEquals("Suite: before method", "setup", suite.getBeforeMethod().getName());
		assertEquals("Suite: after method", "teardown", suite.getAfterMethod().getName());
		assertEquals("Suite: test method list (size)", 2, suite.getTestMethodList().size());
		
		assertEquals("Suite: test method 0", "testNothing0", suite.getTestMethodList().get(0).getName());
		assertEquals("Suite: test method 1", "testNothing1", suite.getTestMethodList().get(1).getName());
		
		setTestsOk();
	}
}
