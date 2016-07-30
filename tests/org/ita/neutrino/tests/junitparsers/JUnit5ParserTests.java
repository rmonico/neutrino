package org.ita.neutrino.tests.junitparsers;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.codeparser.CodeElement;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.astparser.ASTParser;
import org.ita.neutrino.tparsers.junit5parser.JUnit5Parser;
import org.ita.neutrino.tparsers.junit5parser.JUnitAction;
import org.ita.neutrino.tparsers.junit5parser.JUnitAssertion;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitGenericParser;

public class JUnit5ParserTests extends JUnitParserTests {
	
	protected void prepareTests() throws JavaModelException, ParserException {
		StringBuilder mockClassCode = new StringBuilder();

		mockClassCode.append("package org.ita.neutrino.testfiles.junit5parsertests;\n");
		mockClassCode.append("\n");
		mockClassCode.append("import static org.junit.jupiter.api.Assertions.assertTrue;\n");
		mockClassCode.append("\n");
		mockClassCode.append("import org.junit.jupiter.api.AfterEach;\n");
		mockClassCode.append("import org.junit.jupiter.api.BeforeEach;\n");
		mockClassCode.append("import org.junit.jupiter.api.Test;\n");
		mockClassCode.append("\n");
		mockClassCode.append("public class MockClass {\n");
		mockClassCode.append("\n");
		mockClassCode.append("    private Object fixture0 = new Object();\n");
		mockClassCode.append("    private Object fixture1 = new Object();\n");
		mockClassCode.append("    \n");
		mockClassCode.append("    @BeforeEach\n");
		mockClassCode.append("    public void setUp() {\n");
		mockClassCode.append("        action();\n");
		mockClassCode.append("    }\n");
		mockClassCode.append("    \n");
		mockClassCode.append("    @Test\n");
		mockClassCode.append("    public void testNothing0() {\n");
		mockClassCode.append("        action();\n");
		mockClassCode.append("        \n");
		mockClassCode.append("        assertTrue(true, \"Comment\");\n");
		mockClassCode.append("    }\n");
		mockClassCode.append("\n");
		mockClassCode.append("\n");
		mockClassCode.append("    @Test\n");
		mockClassCode.append("    public void testNothing1() {\n");
		mockClassCode.append("        action();\n");
		mockClassCode.append("        \n");
		mockClassCode.append("        assertTrue(true, \"Comment\");\n");
		mockClassCode.append("    }\n");
		mockClassCode.append("\n");
		mockClassCode.append("    \n");
		mockClassCode.append("    private void action() {\n");
		mockClassCode.append("        \n");
		mockClassCode.append("    }\n");
		mockClassCode.append("    \n");
		mockClassCode.append("    @AfterEach\n");
		mockClassCode.append("    public void tearDown() {\n");
		mockClassCode.append("        \n");
		mockClassCode.append("    }\n");
		mockClassCode.append("}\n");

		ICompilationUnit mockCompilationUnit = createSourceFile("org.ita.neutrino.testfiles.junit5parsertests", "MockClass.java", mockClassCode);
		
		codeParser = new ASTParser();
		
		codeParser.setActiveCompilationUnit(mockCompilationUnit);
		codeParser.setCompilationUnits(new ICompilationUnit[] {mockCompilationUnit});
		
		codeParser.parse();
	}
	
	protected void testSuiteParser() {
		assertEquals("Suite: parent", battery, suite.getParent());
		
		CodeElement expectedSuiteCodeElement = codeParser.getEnvironment().getTypeCache().get("org.ita.neutrino.testfiles.junit5parsertests.MockClass");
		
		assertEquals("Suite: code element", expectedSuiteCodeElement, suite.getCodeElement());
	}
	
	@Override
	protected void testAssertions() {
		assertEquals("Assertion: classe", statementList.get(1).getClass(), getAssertionClass());
		
		JUnitAssertion assertion = (JUnitAssertion) statementList.get(1);
		
		assertEquals("Assertion: valor", "assertTrue(true,\"Comment\");\n", assertion.toString());
				
		assertEquals("Assertion: comentário", "Comment", assertion.getExplanation());
	}

	@Override
	protected JUnitGenericParser instantiateJUnitParser() {
		return new JUnit5Parser();
	}

	@Override
	protected Class<JUnitAction> getActionClass() {
		return JUnitAction.class;
	}

	@Override
	protected Class<JUnitAssertion> getAssertionClass() {
		return JUnitAssertion.class;
	}

}
