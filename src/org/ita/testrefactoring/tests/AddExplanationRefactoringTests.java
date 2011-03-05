package org.ita.testrefactoring.tests;

import static org.zero.utils.JUnitUtils.assertBlockEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.testrefactoring.RefactoringException;
import org.ita.testrefactoring.abstracttestparser.TestBattery;
import org.ita.testrefactoring.addexplanation.AddExplanationRefactoring;
import org.ita.testrefactoring.junitparser.JUnitParser;
import org.junit.Test;

public class AddExplanationRefactoringTests extends RefactoringAbstractTests {

	@Test
	public void testAddExplanationToAssertionNewRefactoring() throws JavaModelException, RefactoringException {

		List<ICompilationUnit> compilationUnits = new ArrayList<ICompilationUnit>();

		StringBuilder beforeRefactoringSource = new StringBuilder();

		beforeRefactoringSource.append("package tests.addexplanation;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import static org.junit.Assert.assertEquals;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import org.junit.Test;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("public class TestNotas {\n");
		beforeRefactoringSource.append("\n");
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

		ICompilationUnit activeCompilationUnit = createSourceFile("tests.addexplanation", "TestNotas.java", beforeRefactoringSource);
		ICompilationUnit refactoringTarget = activeCompilationUnit;
		compilationUnits.add(activeCompilationUnit);

		StringBuilder productionClassCode = new StringBuilder();

		productionClassCode.append("package tests.addexplanation;\n");
		productionClassCode.append("\n");
		productionClassCode.append("public class Notas {\n");
		productionClassCode.append("\n");
		productionClassCode.append("    public void add(String aluno, double nota) {\n");
		productionClassCode.append("    }\n");
		productionClassCode.append("\n");
		productionClassCode.append("    public double avg() {\n");
		productionClassCode.append("        return 8.0;\n");
		productionClassCode.append("    }\n");
		productionClassCode.append("\n");
		productionClassCode.append("}\n");

		compilationUnits.add(createSourceFile("tests.addexplanation", "Notas.java", productionClassCode));

		// Invocação da refatoração
		JUnitParser parser = new JUnitParser();

		parser.setActiveCompilationUnit(activeCompilationUnit);
		parser.setCompilationUnits(compilationUnits);

		parser.getSelection().setSourceFile(activeCompilationUnit);
		parser.getSelection().setSelectionStart(307);
		parser.getSelection().setSelectionLength(12);

		parser.parse();

		TestBattery battery = parser.getBattery();

		AddExplanationRefactoring refactoring = new AddExplanationRefactoring();

		refactoring.setBattery(battery);

		refactoring.setExplanationString("Média da turma");

		// Define em que arquivo fonte e local será feita a refatoração
		refactoring.setTargetFragment(parser.getSelection().getSelectedFragment());

		refactoring.refactor();

		// Verificação
		String afterRefactoringSource = refactoringTarget.getSource();

		StringBuffer expectedSource = new StringBuffer();

		expectedSource.append("package tests.addexplanation;\n");
		expectedSource.append("\n");
		expectedSource.append("import static org.junit.Assert.assertEquals;\n");
		expectedSource.append("\n");
		expectedSource.append("import org.junit.Test;\n");
		expectedSource.append("\n");
		expectedSource.append("public class TestNotas {\n");
		expectedSource.append("\n");
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

		assertBlockEquals("Adicionar explicação a asserção", expectedSource.toString(), afterRefactoringSource);
	}

}
