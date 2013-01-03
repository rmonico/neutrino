package org.ita.neutrino.tests.astparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.codeparser.Block;
import org.ita.neutrino.codeparser.Method;
import org.ita.neutrino.codeparser.MethodInvocationExpression;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.Type;
import org.ita.neutrino.codeparser.VariableDeclarationStatement;
import org.ita.neutrino.codeparser.astparser.ASTEnvironment;
import org.ita.neutrino.codeparser.astparser.ASTParser;
import org.ita.neutrino.tests.PluginAbstractTests;
import org.junit.Test;

public class TestSupportedBlockSintax extends PluginAbstractTests {

	private ICompilationUnit blockSupportedSintax;

	private void prepareTests() throws JavaModelException {
		StringBuilder blockSupportedSource = new StringBuilder();

		blockSupportedSource.append("package org.ita.neutrino.testfiles;\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("public class BlockSupportedSintax {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("    @SuppressWarnings(\"unused\")\n");
		blockSupportedSource.append("    public void variableDeclaration() {\n");
		blockSupportedSource.append("        int nonInitializedVariable;\n");
		blockSupportedSource.append("        int initializedVariable = -1;\n");
		blockSupportedSource.append("        int methodInitializedVariable = returnStatement();\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        Integer nonInitializedObject;\n");
		blockSupportedSource.append("        Integer nullInitializedObject = null;\n");
		blockSupportedSource.append("        Integer constructedObject = new Integer(99);\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("    \n");
		blockSupportedSource.append("    // Reaproveitado para testar o return statement\n");
		blockSupportedSource.append("    private int returnStatement() {\n");
		blockSupportedSource.append("        return 55;\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("    public void ifStatements() {\n");
		blockSupportedSource.append("        boolean b = false;\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        // if sem else\n");
		blockSupportedSource.append("        if (b) {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        // if com else\n");
		blockSupportedSource.append("        if (b) {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        } else {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        // Condicional ternário\n");
		blockSupportedSource.append("        b = b ? b : b;\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("    @SuppressWarnings(\"unused\")\n");
		blockSupportedSource.append("    public void controlStructures(boolean avoidUnreachableCode) {\n");
		blockSupportedSource.append("        // for simples\n");
		blockSupportedSource.append("        for (int i = 0; i < 10; i++) {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        // enhanced for\n");
		blockSupportedSource.append("        for (Object o : new Object[3]) {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        while (avoidUnreachableCode) {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        do {\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        } while (avoidUnreachableCode);\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        // non-labeled continue\n");
		blockSupportedSource.append("        while (avoidUnreachableCode) {\n");
		blockSupportedSource.append("            continue;\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        // non-labeled break\n");
		blockSupportedSource.append("        while (avoidUnreachableCode) {\n");
		blockSupportedSource.append("            break;\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("    \n");
		blockSupportedSource.append("    @SuppressWarnings(\"unused\")\n");
		blockSupportedSource.append("    public void explicityCast() {\n");
		blockSupportedSource.append("        Object o = null;\n");
		blockSupportedSource.append("        BlockSupportedSintax b = (BlockSupportedSintax)o;\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("    \n");
		blockSupportedSource.append("    @SuppressWarnings(\"unused\")\n");
		blockSupportedSource.append("    public void expressions() {\n");
		blockSupportedSource.append("        int constantInteger = 0;\n");
		blockSupportedSource.append("        double constantFloat = 0.1;\n");
		blockSupportedSource.append("        String constantString = \"constantString\";\n");
		blockSupportedSource.append("        String stringConcatenation = \"string\" + \" concatenation\";\n");
		blockSupportedSource.append("        int add = 55 + 89;\n");
		blockSupportedSource.append("        int sub = 55 - 89;\n");
		blockSupportedSource.append("        int multiplication = 55 * 89;\n");
		blockSupportedSource.append("        int division = 55 / 89;\n");
		blockSupportedSource.append("        int modulus = 55 % 89;\n");
		blockSupportedSource.append("\n");
		blockSupportedSource.append("        boolean constantBoolean = true;\n");
		blockSupportedSource.append("        boolean optimizedAnd = false && true;\n");
		blockSupportedSource.append("        boolean optimizedOr = true || false;\n");
		blockSupportedSource.append("        boolean completeEvalAnd = false & true;\n");
		blockSupportedSource.append("        boolean completeEvalOr = true | false;\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        boolean equalsOperator = true == false;\n");
		blockSupportedSource.append("        boolean notEqualsOperator = true != false;\n");
		blockSupportedSource.append("        boolean notOperator = !true;\n");
		blockSupportedSource.append("        boolean greaterThan = 0 > 1;\n");
		blockSupportedSource.append("        boolean greaterThanOrEqualTo = 0 >= 1;\n");
		blockSupportedSource.append("        boolean lessThan = 0 < 1;\n");
		blockSupportedSource.append("        boolean lessThanOrEqualTo = 0 <= 1;\n");
		blockSupportedSource.append("        boolean instanceOfOperator = new Object() instanceof Object; \n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        Object nullExpression = null;\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        int assignmentStatement;\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        assignmentStatement = 1;\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        assignmentStatement += 1;\n");
		blockSupportedSource.append("        assignmentStatement -= 1;\n");
		blockSupportedSource.append("        assignmentStatement *= 1;\n");
		blockSupportedSource.append("        assignmentStatement /= 1;\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("    \n");
		blockSupportedSource.append("    public void exceptions() {\n");
		blockSupportedSource.append("        try {\n");
		blockSupportedSource.append("            throw new Error();\n");
		blockSupportedSource.append("        } catch (Error e) {\n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("        \n");
		blockSupportedSource.append("        try {\n");
		blockSupportedSource.append("            \n");
		blockSupportedSource.append("        } finally {\n");
		blockSupportedSource.append("            \n");
		blockSupportedSource.append("        }\n");
		blockSupportedSource.append("    }\n");
		blockSupportedSource.append("}\n");

		blockSupportedSintax = createSourceFile("org.ita.neutrino.testfiles", "BlockSupportedSintax.java", blockSupportedSource);
	}

	@Test
	public void testSupportedBlockSintax() throws JavaModelException, ParserException {
		prepareTests();

		ASTParser parser = new ASTParser();

		parser.setCompilationUnits(new ICompilationUnit[] {blockSupportedSintax});
		parser.setActiveCompilationUnit(blockSupportedSintax);

		parser.parse();

		ASTEnvironment environment = parser.getEnvironment();

		Type clazz = environment.getTypeCache().get("org.ita.neutrino.testfiles.BlockSupportedSintax");

		Method method = (Method) clazz.getMethodList().get("variableDeclaration");

		Block block = method.getBody();

		assertEquals("Lista de statements (size)", 6, block.getStatementList().size());

		assertTrue("Tipo do statement", block.getStatementList().get(0) instanceof VariableDeclarationStatement);

		VariableDeclarationStatement nonInitializedVariable = (VariableDeclarationStatement) block.getStatementList().get(0);

		assertEquals("Declaração de variável sem inicialização (Name)", "nonInitializedVariable", nonInitializedVariable.getVariableName());
		assertEquals("Declaração de variável sem inicialização (Type)", "<primitive type package>.int", nonInitializedVariable.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável sem inicialização (Initialization)", null, nonInitializedVariable.getInitialization());

		VariableDeclarationStatement initializedVariable = (VariableDeclarationStatement) block.getStatementList().get(1);

		assertEquals("Declaração de variável com inicialização literal (Name)", "initializedVariable", initializedVariable.getVariableName());
		assertEquals("Declaração de variável com inicialização literal (Type)", "<primitive type package>.int", initializedVariable.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável com inicialização literal (Initialization)", "-1", initializedVariable.getInitialization().toString());

		VariableDeclarationStatement methodInitializedVariable = (VariableDeclarationStatement) block.getStatementList().get(2);

		assertEquals("Declaração de variável inicializada por método (Tipo)", "methodInitializedVariable", methodInitializedVariable.getVariableName());
		assertEquals("Declaração de variável inicializada por método (Type)", "<primitive type package>.int", methodInitializedVariable.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável inicializada por método (Initialization)", clazz.getMethodList().get("returnStatement"), ((MethodInvocationExpression) methodInitializedVariable.getInitialization()).getCalledMethod());

		VariableDeclarationStatement nonInitializedObject = (VariableDeclarationStatement) block.getStatementList().get(3);

		assertEquals("Declaração de variável objeto não inicializada (Tipo)", "nonInitializedObject", nonInitializedObject.getVariableName());
		assertEquals("Declaração de variável objeto não inicializada (Type)", "java.lang.Integer", nonInitializedObject.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável objeto não inicializada (Initialization)", null, nonInitializedObject.getInitialization());

		VariableDeclarationStatement nullInitializedObject = (VariableDeclarationStatement) block.getStatementList().get(4);

		assertEquals("Declaração de variável objeto inicializada com null (Tipo)", "nullInitializedObject", nullInitializedObject.getVariableName());
		assertEquals("Declaração de variável objeto inicializada com null (Type)", "java.lang.Integer", nullInitializedObject.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável objeto inicializada com null (Initialization)", "null", nullInitializedObject.getInitialization().toString());

		VariableDeclarationStatement constructedObject = (VariableDeclarationStatement) block.getStatementList().get(5);

		assertEquals("Declaração de variável objeto inicializada por construtor (Tipo)", "constructedObject", constructedObject.getVariableName());
		assertEquals("Declaração de variável objeto inicializada por construtor (Type)", "java.lang.Integer", constructedObject.getVariableType().getQualifiedName());
		assertEquals("Declaração de variável objeto inicializada por construtor (Initialization)", "new Integer(99)", constructedObject.getInitialization().toString());
	}

}
