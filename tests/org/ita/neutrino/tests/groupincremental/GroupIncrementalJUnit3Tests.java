package org.ita.neutrino.tests.groupincremental;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.astparser.ASTParser;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class GroupIncrementalJUnit3Tests extends GroupIncrementalTests {

	@Override
	protected void prepareTests() throws JavaModelException, ParserException, TestParserException {
		List<ICompilationUnit> compilationUnits = new ArrayList<ICompilationUnit>();

		StringBuilder beforeRefactoringSource = getOriginTestSource();

		refactoredCompilationUnit = createSourceFile("org.ita.neutrino.addfixturerefactoring", "TestConnectJUnit3.java", beforeRefactoringSource);
		compilationUnits.add(refactoredCompilationUnit);

		StringBuilder productionClassCode = getProductionSource();

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

		expectedSource.append("package org.ita.neutrino.groupincrementalrefactoring;\n");
		expectedSource.append("\n");
		expectedSource.append("import junit.framework.Assert;\n");
		expectedSource.append("import junit.framework.TestCase;\n");
		expectedSource.append("import org.ita.neutrino.businessclasses.Carrinho;\n");
		expectedSource.append("\n");
		expectedSource.append("public class TestCarrinhoJUnit3Expected extends TestCase {\n");
		expectedSource.append("	Carrinho carrinho;\n");
		expectedSource.append("\n");
		expectedSource.append("	public void setUp() {\n");
		expectedSource.append("		carrinho = new Carrinho();\n");
		expectedSource.append("	}\n");
		expectedSource.append("\n");
		expectedSource.append("	public void testAdicaoDeItens() {\n");
		expectedSource.append("		carrinho.adicionarItem(100);\n");
		expectedSource.append("		Assert.assertEquals(\"Item + Entrega\", 115, carrinho.totalCompra());\n");
		expectedSource.append("		carrinho.adicionarItem(100);\n");
		expectedSource.append("		Assert.assertEquals(\"Itens sem taxa\", 200, carrinho.totalCompra());\n");
		expectedSource.append("		carrinho.adicionarItem(100);\n");
		expectedSource.append("		Assert.assertEquals(\"Itens menos 5%\", 285, carrinho.totalCompra());\n");
		expectedSource.append("	}\n");
		expectedSource.append("}\n");

		return expectedSource;
	}

	private StringBuilder getOriginTestSource() {
		StringBuilder beforeRefactoringSource = new StringBuilder();

		beforeRefactoringSource.append("package org.ita.neutrino.groupincrementalrefactoring;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import junit.framework.Assert;\n");
		beforeRefactoringSource.append("import junit.framework.TestCase;\n");
		beforeRefactoringSource.append("import org.ita.neutrino.businessclasses.Carrinho;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("public class TestCarrinhoJUnit3 extends TestCase {\n");
		beforeRefactoringSource.append("	Carrinho carrinho;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("	public void setUp() {\n");
		beforeRefactoringSource.append("		carrinho = new Carrinho();\n");
		beforeRefactoringSource.append("	}\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("	public void testUmItem() {\n");
		beforeRefactoringSource.append("		carrinho.adicionarItem(100);\n");
		beforeRefactoringSource.append("		Assert.assertEquals(\"Item + Entrega\", 115, carrinho.totalCompra());\n");
		beforeRefactoringSource.append("	}\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("	public void testDoisItens() {\n");
		beforeRefactoringSource.append("		carrinho.adicionarItem(100);\n");
		beforeRefactoringSource.append("		carrinho.adicionarItem(100);\n");
		beforeRefactoringSource.append("		Assert.assertEquals(\"Itens sem taxa\", 200, carrinho.totalCompra());\n");
		beforeRefactoringSource.append("	}\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("	public void testTresItens() {\n");
		beforeRefactoringSource.append("		carrinho.adicionarItem(100);\n");
		beforeRefactoringSource.append("		carrinho.adicionarItem(100);\n");
		beforeRefactoringSource.append("		carrinho.adicionarItem(100);\n");
		beforeRefactoringSource.append("		Assert.assertEquals(\"Itens menos 5%\", 285, carrinho.totalCompra());\n");
		beforeRefactoringSource.append("	}\n");
		beforeRefactoringSource.append("}\n");

		return beforeRefactoringSource;
	}

}
