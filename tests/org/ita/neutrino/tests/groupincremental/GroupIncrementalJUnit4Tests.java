package org.ita.neutrino.tests.groupincremental;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.codeparser.astparser.ASTParser;
import org.ita.neutrino.tparsers.abstracttestparser.TestParserException;
import org.ita.neutrino.tparsers.junit3parser.JUnit3Parser;

public class GroupIncrementalJUnit4Tests extends GroupIncrementalTests {

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
		StringBuilder sb = new StringBuilder();

		sb.append("package org.ita.neutrino.groupincrementalrefactoring;\n");
		sb.append("\n");
		sb.append("import junit.framework.Assert;\n");
		sb.append("import org.ita.neutrino.businessclasses.Carrinho;\n");
		sb.append("import org.junit.Before;\n");
		sb.append("import org.junit.Test;\n");
		sb.append("\n");
		sb.append("public class TestCarrinhoJUnit4Expected {\n");
		sb.append("    Carrinho carrinho;\n");
		sb.append("\n");
		sb.append("    @Before\n");
		sb.append("    public void setUp() {\n");
		sb.append("        carrinho = new Carrinho();\n");
		sb.append("    }\n");
		sb.append("\n");
		sb.append("    @Test\n");
		sb.append("    public void testAdicaoDeItens() {\n");
		sb.append("        carrinho.adicionarItem(100);\n");
		sb.append("        Assert.assertEquals(\"Item + Entrega\", 115, carrinho.totalCompra());\n");
		sb.append("        carrinho.adicionarItem(100);\n");
		sb.append("        Assert.assertEquals(\"Itens sem taxa\", 200, carrinho.totalCompra());\n");
		sb.append("        carrinho.adicionarItem(100);\n");
		sb.append("        Assert.assertEquals(\"Itens menos 5%\", 285, carrinho.totalCompra());\n");
		sb.append("    }\n");
		sb.append("}\n");

		return sb;
	}

	private StringBuilder getOriginTestSource() {
		StringBuilder sb = new StringBuilder();

		sb.append("package org.ita.neutrino.groupincrementalrefactoring;\n");
		sb.append("\n");
		sb.append("import junit.framework.Assert;\n");
		sb.append("import org.ita.neutrino.businessclasses.Carrinho;\n");
		sb.append("import org.junit.Before;\n");
		sb.append("import org.junit.Test;\n");
		sb.append("\n");
		sb.append("public class TestCarrinhoJUnit4 {\n");
		sb.append("    Carrinho carrinho;\n");
		sb.append("\n");
		sb.append("    @Before\n");
		sb.append("    public void setUp() {\n");
		sb.append("        carrinho = new Carrinho();\n");
		sb.append("    }\n");
		sb.append("\n");
		sb.append("    @Test\n");
		sb.append("    public void testUmItem() {\n");
		sb.append("        carrinho.adicionarItem(100);\n");
		sb.append("        Assert.assertEquals(\"Item + Entrega\", 115, carrinho.totalCompra());\n");
		sb.append("    }\n");
		sb.append("\n");
		sb.append("    @Test\n");
		sb.append("    public void testDoisItens() {\n");
		sb.append("        carrinho.adicionarItem(100);\n");
		sb.append("        carrinho.adicionarItem(100);\n");
		sb.append("        Assert.assertEquals(\"Itens sem taxa\", 200, carrinho.totalCompra());\n");
		sb.append("    }\n");
		sb.append("\n");
		sb.append("    @Test\n");
		sb.append("    public void testTresItens() {\n");
		sb.append("        carrinho.adicionarItem(100);\n");
		sb.append("        carrinho.adicionarItem(100);\n");
		sb.append("        carrinho.adicionarItem(100);\n");
		sb.append("        Assert.assertEquals(\"Itens menos 5%\", 285, carrinho.totalCompra());\n");
		sb.append("    }\n");
		sb.append("}\n");


		return sb;
	}

}
