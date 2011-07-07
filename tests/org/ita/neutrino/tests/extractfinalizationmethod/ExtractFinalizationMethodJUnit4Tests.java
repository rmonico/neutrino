package org.ita.neutrino.tests.extractfinalizationmethod;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.astparser.ASTParser;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.junit4parser.JUnit4Parser;

public class ExtractFinalizationMethodJUnit4Tests extends ExtractFinalizationMethodTests {

	protected void prepareTests() throws JavaModelException, ParserException, TestParserException {
		List<ICompilationUnit> compilationUnits = new ArrayList<ICompilationUnit>();

		StringBuilder beforeRefactoringSource = new StringBuilder();

		beforeRefactoringSource.append("package org.ita.neutrino.extractfinalizationmethod;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import static org.junit.Assert.assertFalse;\n");
		beforeRefactoringSource.append("import static org.junit.Assert.assertTrue;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import org.junit.Test;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("public class TestConnectJUnit4 {\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("    Connect connect;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("    @Test\n");
		beforeRefactoringSource.append("    public void testConecta() {\n");
		beforeRefactoringSource.append("        connect = new Connect();\n");
		beforeRefactoringSource.append("        connect.setPorta(8080);\n");
		beforeRefactoringSource.append("        connect.setIP(\"127.0.0.1\");\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("        connect.estabelecerConexao();\n");
		beforeRefactoringSource.append("        assertTrue(\"Conexão Estabelecida\", connect.isConectado());\n");
		beforeRefactoringSource.append("        assertFalse(\"Esperando Conexão\", connect.isListen());\n");
		beforeRefactoringSource.append("        connect.fecharConexao();\n");
		beforeRefactoringSource.append("    }\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("    @Test\n");
		beforeRefactoringSource.append("    public void testListen() {\n");
		beforeRefactoringSource.append("        connect = new Connect();\n");
		beforeRefactoringSource.append("        connect.setPorta(8080);\n");
		beforeRefactoringSource.append("        connect.setIP(\"127.0.0.1\");\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("        connect.escutarConexao();\n");
		beforeRefactoringSource.append("        assertFalse(\"Conexão Estabelecida\", connect.isConectado());\n");
		beforeRefactoringSource.append("        assertTrue(\"Esperando Conexão\", connect.isListen());\n");
		beforeRefactoringSource.append("        connect.fecharConexao();\n");
		beforeRefactoringSource.append("    }\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("}\n");

		refactoredCompilationUnit = createSourceFile("org.ita.neutrino.extractfinalizationmethod", "TestConnectJUnit4.java", beforeRefactoringSource);
		compilationUnits.add(refactoredCompilationUnit);

		StringBuilder productionClassCode = new StringBuilder();

		productionClassCode.append("package org.ita.neutrino.extractfinalizationmethod;\n");
		productionClassCode.append("\n");
		productionClassCode.append("public class Connect {\n");
		productionClassCode.append("\n");
		productionClassCode.append("    public void setPorta(int i) {\n");
		productionClassCode.append("        // TODO Auto-generated method stub\n");
		productionClassCode.append("        \n");
		productionClassCode.append("    }\n");
		productionClassCode.append("\n");
		productionClassCode.append("    public void setIP(String string) {\n");
		productionClassCode.append("        // TODO Auto-generated method stub\n");
		productionClassCode.append("        \n");
		productionClassCode.append("    }\n");
		productionClassCode.append("\n");
		productionClassCode.append("    public void estabelecerConexao() {\n");
		productionClassCode.append("        // TODO Auto-generated method stub\n");
		productionClassCode.append("        \n");
		productionClassCode.append("    }\n");
		productionClassCode.append("\n");
		productionClassCode.append("    public boolean isConectado() {\n");
		productionClassCode.append("        // TODO Auto-generated method stub\n");
		productionClassCode.append("        return false;\n");
		productionClassCode.append("    }\n");
		productionClassCode.append("\n");
		productionClassCode.append("    public boolean isListen() {\n");
		productionClassCode.append("        // TODO Auto-generated method stub\n");
		productionClassCode.append("        return false;\n");
		productionClassCode.append("    }\n");
		productionClassCode.append("\n");
		productionClassCode.append("    public void fecharConexao() {\n");
		productionClassCode.append("        // TODO Auto-generated method stub\n");
		productionClassCode.append("        \n");
		productionClassCode.append("    }\n");
		productionClassCode.append("\n");
		productionClassCode.append("    public void escutarConexao() {\n");
		productionClassCode.append("        // TODO Auto-generated method stub\n");
		productionClassCode.append("        \n");
		productionClassCode.append("    }\n");
		productionClassCode.append("\n");
		productionClassCode.append("}\n");

		ICompilationUnit productionCompilationUnit = createSourceFile("org.ita.neutrino.extractfinalizationmethod", "Connect.java", productionClassCode);
		compilationUnits.add(productionCompilationUnit);

		ASTParser codeParser = new ASTParser();

		codeParser.setActiveCompilationUnit(refactoredCompilationUnit);
		codeParser.setCompilationUnits(compilationUnits.toArray(new ICompilationUnit[0]));

		CodeSelection selection = codeParser.getSelection();

		selection.setSourceFile(refactoredCompilationUnit);
		selection.setSelectionStart(178);
		selection.setSelectionLength(17);

		codeParser.parse();

		testParser = new JUnit4Parser();

		testParser.setEnvironment(codeParser.getEnvironment());

		testParser.parse();
	}

	protected StringBuilder getExpectedSource() {
		StringBuilder expectedSource = new StringBuilder();

		expectedSource.append("package org.ita.neutrino.extractfinalizationmethod;\n");
		expectedSource.append("\n");
		expectedSource.append("import static org.junit.Assert.assertFalse;\n");
		expectedSource.append("import static org.junit.Assert.assertTrue;\n");
		expectedSource.append("\n");
		expectedSource.append("import org.junit.Test;\n");
		expectedSource.append("\n");
		expectedSource.append("public class TestConnectJUnit4 {\n");
		expectedSource.append("\n");
		expectedSource.append("    Connect connect;\n");
		expectedSource.append("\n");
		expectedSource.append("    @Test\n");
		expectedSource.append("    public void testConecta() {\n");
		expectedSource.append("        connect = new Connect();\n");
		expectedSource.append("        connect.setPorta(8080);\n");
		expectedSource.append("        connect.setIP(\"127.0.0.1\");\n");
		expectedSource.append("\n");
		expectedSource.append("        connect.estabelecerConexao();\n");
		expectedSource.append("        assertTrue(\"Conexão Estabelecida\", connect.isConectado());\n");
		expectedSource.append("        assertFalse(\"Esperando Conexão\", connect.isListen());\n");
		expectedSource.append("    }\n");
		expectedSource.append("\n");
		expectedSource.append("    @Test\n");
		expectedSource.append("    public void testListen() {\n");
		expectedSource.append("        connect = new Connect();\n");
		expectedSource.append("        connect.setPorta(8080);\n");
		expectedSource.append("        connect.setIP(\"127.0.0.1\");\n");
		expectedSource.append("\n");
		expectedSource.append("        connect.escutarConexao();\n");
		expectedSource.append("        assertFalse(\"Conexão Estabelecida\", connect.isConectado());\n");
		expectedSource.append("        assertTrue(\"Esperando Conexão\", connect.isListen());\n");
		expectedSource.append("    }\n");
		expectedSource.append("\n");
		expectedSource.append("    \n");
		expectedSource.append("    @org.junit.After()\n");
		expectedSource.append("    public void teardown() {\n");
		expectedSource.append("        connect.fecharConexao();\n");
		expectedSource.append("    }\n");
		expectedSource.append("}\n");
		
		return expectedSource;
	}

}
