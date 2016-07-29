package org.ita.neutrino.tests.junitparsers;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.codeparser.CodeElement;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.astparser.ASTParser;
import org.ita.neutrino.tparsers.junit4parser.JUnit4Parser;
import org.ita.neutrino.tparsers.junit4parser.JUnitAction;
import org.ita.neutrino.tparsers.junit4parser.JUnitAssertion;
import org.ita.neutrino.tparsers.junitgenericparser.JUnitGenericParser;

public class JUnit4ParserTests extends JUnitParserTests {
	
	protected void prepareTests() throws JavaModelException, ParserException {
		StringBuilder mockClassCode = new StringBuilder();

		mockClassCode.append("package org.ita.neutrino.testfiles.junit4parsertests;\n");
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
		mockClassCode.append("    public void setUp() {\n");
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
		mockClassCode.append("    public void tearDown() {\n");
		mockClassCode.append("        \n");
		mockClassCode.append("    }\n");
		mockClassCode.append("}\n");

		ICompilationUnit mockCompilationUnit = createSourceFile("org.ita.neutrino.testfiles.junit4parsertests", "MockClass.java", mockClassCode);
		
		codeParser = new ASTParser();
		
		codeParser.setActiveCompilationUnit(mockCompilationUnit);
		codeParser.setCompilationUnits(new ICompilationUnit[] {mockCompilationUnit});
		
		codeParser.parse();
	}
	
	protected void testSuiteParser() {
		assertEquals("Suite: parent", battery, suite.getParent());
		
		CodeElement expectedSuiteCodeElement = codeParser.getEnvironment().getTypeCache().get("org.ita.neutrino.testfiles.junit4parsertests.MockClass");
		
		assertEquals("Suite: code element", expectedSuiteCodeElement, suite.getCodeElement());
	}

	@Override
	protected JUnitGenericParser instantiateJUnitParser() {
		return new JUnit4Parser();
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
