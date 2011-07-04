package org.ita.neutrino.tests.addexplanation;

import static org.zero.utils.JUnitUtils.assertBlockEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.TestBattery;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.addexplanation.AddExplanationRefactoring;
import org.ita.neutrino.astparser.ASTParser;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.junit4parser.JUnit4Parser;
import org.ita.neutrino.tests.RefactoringAbstractTests;
import org.junit.Test;

public class AddExplanationJUnit4Tests extends RefactoringAbstractTests {

	private ICompilationUnit refactoredCompilationUnit;
	private JUnit4Parser testParser;
	
	private void prepareTests() throws JavaModelException, ParserException, TestParserException {
		List<ICompilationUnit> compilationUnits = new ArrayList<ICompilationUnit>();

		StringBuilder beforeRefactoringSource = new StringBuilder();

		beforeRefactoringSource.append("package org.ita.neutrino.addexplanationrefactoring;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import static org.junit.Assert.assertEquals;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import org.junit.Test;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("public class TestNotasJUnit4 {\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("    @SuppressWarnings(\"unused\")\n");
		beforeRefactoringSource.append("    @Test\n");
		beforeRefactoringSource.append("    public void testNotasTurma() {\n");
		beforeRefactoringSource.append("        Notas not = new Notas();\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("        not.add(\"João\", 8.0);\n");
		beforeRefactoringSource.append("        not.add(\"Pedro\", 7.0);\n");
		beforeRefactoringSource.append("        not.add(\"Maria\", 9.0);\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("        assertEquals(not.avg(), 8.0, 0);\n");
		beforeRefactoringSource.append("    }\n");
		beforeRefactoringSource.append("}\n");

		refactoredCompilationUnit = createSourceFile("org.ita.neutrino.addexplanationrefactoring", "TestNotasJUnit4.java", beforeRefactoringSource);
		compilationUnits.add(refactoredCompilationUnit);

		StringBuilder productionClassCode = new StringBuilder();

		productionClassCode.append("package org.ita.neutrino.addexplanationrefactoring;\n");
		productionClassCode.append("\n");
		productionClassCode.append("public class Notas {\n");
		productionClassCode.append("    public void add(String aluno, double nota) {\n");
		productionClassCode.append("        \n");
		productionClassCode.append("    }\n");
		productionClassCode.append("    \n");
		productionClassCode.append("    public double avg() {\n");
		productionClassCode.append("        return 8.0;\n");
		productionClassCode.append("    }\n");
		productionClassCode.append("\n");
		productionClassCode.append("}\n");

		compilationUnits.add(createSourceFile("org.ita.neutrino.addexplanationrefactoring", "Notas.java", productionClassCode));

		ASTParser codeParser = new ASTParser();
		
		codeParser.setActiveCompilationUnit(refactoredCompilationUnit);
		codeParser.setCompilationUnits(compilationUnits.toArray(new ICompilationUnit[0]));
		
		CodeSelection selection = codeParser.getSelection();
		
		selection.setSourceFile(refactoredCompilationUnit);
		selection.setSelectionStart(375);
		selection.setSelectionLength(12);
		
		codeParser.parse();

		testParser = new JUnit4Parser();
		
		testParser.setEnvironment(codeParser.getEnvironment());
		
		testParser.parse();

	}

	private StringBuilder getExpectedSource() {
		StringBuilder expectedSource = new StringBuilder();

		expectedSource.append("package org.ita.neutrino.addexplanationrefactoring;\n");
		expectedSource.append("\n");
		expectedSource.append("import static org.junit.Assert.assertEquals;\n");
		expectedSource.append("\n");
		expectedSource.append("import org.junit.Test;\n");
		expectedSource.append("\n");
		expectedSource.append("public class TestNotasJUnit4 {\n");
		expectedSource.append("\n");
		expectedSource.append("    @SuppressWarnings(\"unused\")\n");
		expectedSource.append("    @Test\n");
		expectedSource.append("    public void testNotasTurma() {\n");
		expectedSource.append("        Notas not = new Notas();\n");
		expectedSource.append("\n");
		expectedSource.append("        not.add(\"João\", 8.0);\n");
		expectedSource.append("        not.add(\"Pedro\", 7.0);\n");
		expectedSource.append("        not.add(\"Maria\", 9.0);\n");
		expectedSource.append("\n");
		expectedSource.append("        assertEquals(\"Média da turma\", not.avg(), 8.0, 0);\n");
		expectedSource.append("    }\n");
		expectedSource.append("}\n");
		
		return expectedSource;
	}

	@Test
	public void testAddExplanationToAssertionNewRefactoring() throws JavaModelException, RefactoringException, TestParserException, ParserException {

		prepareTests();
		
		// Aplica a refatoração na bateria de testes
		TestBattery battery = testParser.getBattery();

		AddExplanationRefactoring refactoring = new AddExplanationRefactoring();

		refactoring.setBattery(battery);

		refactoring.setExplanationString("Média da turma");

		// Define em que arquivo fonte e local será feita a refatoração
		refactoring.setTargetFragment(battery.getSelection().getSelectedFragment());

		refactoring.refactor();

		// Verificação
		String afterRefactoringSource = refactoredCompilationUnit.getSource();

		StringBuilder expectedSource = getExpectedSource();

		assertBlockEquals("Adicionar explicação a asserção", expectedSource.toString(), afterRefactoringSource);
	}

}
