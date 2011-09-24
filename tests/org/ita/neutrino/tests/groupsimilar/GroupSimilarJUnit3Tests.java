package org.ita.neutrino.tests.groupsimilar;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.astparser.ASTParser;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class GroupSimilarJUnit3Tests extends GroupSimilarTests {

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

		expectedSource.append("package org.ita.neutrino.groupsimilarrefactoring;\n");
		expectedSource.append("\n");
		expectedSource.append("import junit.framework.TestCase;\n");
		expectedSource.append("import org.ita.neutrino.businessclasses.CalculadorImposto;\n");
		expectedSource.append("\n");
		expectedSource.append("public class TestCalculadorImpostoJUnit3Expected extends TestCase {\n");
		expectedSource.append("	static int[] valoresSalario = { 300, 600, 2500, 6000 };\n");
		expectedSource.append("	static double[] percentImposto = { 0, 7.3, 9.1, 11.4 };\n");
		expectedSource.append("	static String[] msgs = { \"Sem imposto\", \"7.3% de imposto\",\n");
		expectedSource.append("			\"9.1% de imposto\", \"11.4% de imposto\" };\n");
		expectedSource.append("\n");
		expectedSource.append("	public void testFaixaValores() {\n");
		expectedSource.append("		CalculadorImposto calc = null;\n");
		expectedSource.append("		for (int i = 0; i < valoresSalario.length; i++) {\n");
		expectedSource.append("			calc = new CalculadorImposto(valoresSalario[i]);\n");
		expectedSource.append("			assertEquals(msgs[i], percentImposto[i], calc.getPercentagem());\n");
		expectedSource.append("		}\n");
		expectedSource.append("	}\n");
		expectedSource.append("}\n");

		return expectedSource;
	}

	private StringBuilder getOriginTestSource() {
		StringBuilder beforeRefactoringSource = new StringBuilder();

		beforeRefactoringSource.append("package org.ita.neutrino.groupsimilarrefactoring;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import junit.framework.TestCase;\n");
		beforeRefactoringSource.append("import org.ita.neutrino.businessclasses.CalculadorImposto;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("public class TestCalculadorImpostoJUnit3 extends TestCase {\n");
		beforeRefactoringSource.append("	public void testMenosDe300() {\n");
		beforeRefactoringSource.append("		CalculadorImposto calc = new CalculadorImposto(300);\n");
		beforeRefactoringSource.append("		assertEquals(\"Sem imposto\", 0, calc.getPercentagem());\n");
		beforeRefactoringSource.append("	}\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("	public void testEntre300e1000() {\n");
		beforeRefactoringSource.append("		CalculadorImposto calc = new CalculadorImposto(600);\n");
		beforeRefactoringSource.append("		assertEquals(\"7.3% de imposto\", 7.3, calc.getPercentagem());\n");
		beforeRefactoringSource.append("	}\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("	public void testEntre1000e5000() {\n");
		beforeRefactoringSource.append("		CalculadorImposto calc = new CalculadorImposto(2500);\n");
		beforeRefactoringSource.append("		assertEquals(\"9.1% de imposto\", 9.1, calc.getPercentagem());\n");
		beforeRefactoringSource.append("	}\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("	public void testAcimaDe5000() {\n");
		beforeRefactoringSource.append("		CalculadorImposto calc = new CalculadorImposto(6000);\n");
		beforeRefactoringSource.append("		assertEquals(\"11.4% de imposto\", 11.4, calc.getPercentagem());\n");
		beforeRefactoringSource.append("	}\n");
		beforeRefactoringSource.append("}\n");

		return beforeRefactoringSource;
	}

}
