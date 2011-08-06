package org.ita.neutrino.tests.addfixture;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.addfixture.AddFixtureRefactoring;
import org.ita.neutrino.astparser.ASTParser;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.junit3parser.JUnit3Parser;
import org.ita.neutrino.tests.RefactoringAbstractTests;

public class AddFixtureJUnit3Tests extends RefactoringAbstractTests {

	@Override
	protected void prepareTests() throws JavaModelException, ParserException, TestParserException {
		List<ICompilationUnit> compilationUnits = new ArrayList<ICompilationUnit>();

		StringBuilder beforeRefactoringSource = new StringBuilder();

		beforeRefactoringSource.append("package org.ita.neutrino.addfixturerefactoring;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import junit.framework.TestCase;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import org.ita.neutrino.businessclasses.Connect;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("public class TestConnectJUnit3 extends TestCase {\n");
		beforeRefactoringSource.append("    \n");
		beforeRefactoringSource.append("    public void testConecta() {\n");
		beforeRefactoringSource.append("        Connect connect = new Connect();\n");
		beforeRefactoringSource.append("        connect.setPorta(8080);\n");
		beforeRefactoringSource.append("        connect.setIP(\"127.0.0.1\");\n");
		beforeRefactoringSource.append("        connect.estabelecerConexao();\n");
		beforeRefactoringSource.append("        assertTrue(\"Conexão Estabelecida\", connect.isConectado());\n");
		beforeRefactoringSource.append("        assertFalse(\"Esperando Conexão\", connect.isListen());\n");
		beforeRefactoringSource.append("        connect.fecharConexao();\n");
		beforeRefactoringSource.append("    }\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("    public void testListen() {\n");
		beforeRefactoringSource.append("        Connect connect = new Connect();\n");
		beforeRefactoringSource.append("        connect.setPorta(8080);\n");
		beforeRefactoringSource.append("        connect.setIP(\"127.0.0.1\");\n");
		beforeRefactoringSource.append("        connect.escutarConexao();\n");
		beforeRefactoringSource.append("        assertFalse(\"â€œConexão Estabelecida\", connect.isConectado());\n");
		beforeRefactoringSource.append("        assertTrue(\"Esperando Conexão\", connect.isListen());\n");
		beforeRefactoringSource.append("        connect.fecharConexao();\n");
		beforeRefactoringSource.append("    }\n");
		beforeRefactoringSource.append("}\n");

		refactoredCompilationUnit = createSourceFile("org.ita.neutrino.addfixturerefactoring", "TestConnectJUnit3.java", beforeRefactoringSource);
		compilationUnits.add(refactoredCompilationUnit);

		StringBuilder productionClassCode = new StringBuilder();

		productionClassCode.append("package org.ita.neutrino.businessclasses;\n");
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

		compilationUnits.add(createSourceFile("org.ita.neutrino.businessclasses", "Connect.java", productionClassCode));

		ASTParser codeParser = new ASTParser();

		codeParser.setActiveCompilationUnit(refactoredCompilationUnit);
		codeParser.setCompilationUnits(compilationUnits.toArray(new ICompilationUnit[0]));

		CodeSelection selection = codeParser.getSelection();

		selection.setSourceFile(refactoredCompilationUnit);
		selection.setSelectionStart(236);
		selection.setSelectionLength(7);

		codeParser.parse();

		testParser = new JUnit3Parser();

		testParser.setEnvironment(codeParser.getEnvironment());

		testParser.parse();
	}

	@Override
	protected StringBuilder getExpectedSource() {
		StringBuilder expectedSource = new StringBuilder();

		expectedSource.append("package org.ita.neutrino.addfixturerefactoring;\n");
		expectedSource.append("\n");
		expectedSource.append("import org.ita.neutrino.businessclasses.Connect;\n");
		expectedSource.append("\n");
		expectedSource.append("import junit.framework.TestCase;\n");
		expectedSource.append("\n");
		expectedSource.append("public class TestConnectJUnit3Expected extends TestCase {\n");
		expectedSource.append("    Connect connect;\n");
		expectedSource.append("\n");
		expectedSource.append("    public void testConecta() {\n");
		expectedSource.append("        connect = new Connect();\n");
		expectedSource.append("        connect.setPorta(8080);\n");
		expectedSource.append("        connect.setIP(\"127.0.0.1\");\n");
		expectedSource.append("        connect.estabelecerConexao();\n");
		expectedSource.append("        assertTrue(\"Conexao Estabelecida\", connect.isConectado());\n");
		expectedSource.append("        assertFalse(\"Esperando Conexão\", connect.isListen());\n");
		expectedSource.append("        connect.fecharConexao();\n");
		expectedSource.append("    }\n");
		expectedSource.append("\n");
		expectedSource.append("    public void testListen() {\n");
		expectedSource.append("        connect = new Connect();\n");
		expectedSource.append("        connect.setPorta(8080);\n");
		expectedSource.append("        connect.setIP(\"127.0.0.1\");\n");
		expectedSource.append("        connect.escutarConexao();\n");
		expectedSource.append("        assertFalse(\"Conexão Estabelecida\", connect.isConectado());\n");
		expectedSource.append("        assertTrue(\"Esperando Conexão\", connect.isListen());\n");
		expectedSource.append("        connect.fecharConexao();\n");
		expectedSource.append("    }\n");
		expectedSource.append("}\n");

		return expectedSource;
	}

	@Override
	protected AbstractRefactoring instantiateRefactoring() {
		// TODO Auto-generated method stub
		return new AddFixtureRefactoring();
	}

	@Override
	protected String getRefactoringName() {
		// TODO Auto-generated method stub
		return "Adicionar Fixture";
	}

	@Override
	protected void setupRefactoring() {
		// informa parametros pra refatoracao.
		super.setupRefactoring();
	}

}
