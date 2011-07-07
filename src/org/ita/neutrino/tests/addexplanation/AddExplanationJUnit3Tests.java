package org.ita.neutrino.tests.addexplanation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.astparser.ASTParser;
import org.ita.neutrino.codeparser.CodeSelection;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.junit3parser.JUnit3Parser;

public class AddExplanationJUnit3Tests extends AddExplanationToAssertionTests {

	@Override
	protected void prepareTests() throws JavaModelException, ParserException, TestParserException {
		List<ICompilationUnit> compilationUnits = new ArrayList<ICompilationUnit>();

		StringBuilder beforeRefactoringSource = new StringBuilder();

		beforeRefactoringSource.append("package org.ita.neutrino.addexplanationrefactoring;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("import junit.framework.TestCase;\n");
		beforeRefactoringSource.append("\n");
		beforeRefactoringSource.append("public class TestNotasJUnit3 extends TestCase {\n");
		beforeRefactoringSource.append("    \n");
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

		refactoredCompilationUnit = createSourceFile("org.ita.neutrino.addexplanationrefactoring", "TestNotasJUnit3.java", beforeRefactoringSource);
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
		selection.setSelectionStart(310);
		selection.setSelectionLength(12);
		
		codeParser.parse();
		
		testParser = new JUnit3Parser();
		
		testParser.setEnvironment(codeParser.getEnvironment());
		
		testParser.parse();

	}

	@Override
	protected StringBuilder getExpectedSource() {
		StringBuilder expectedSource = new StringBuilder();

		expectedSource.append("package org.ita.neutrino.addexplanationrefactoring;\n");
		expectedSource.append("\n");
		expectedSource.append("import junit.framework.TestCase;\n");
		expectedSource.append("\n");
		expectedSource.append("public class TestNotasJUnit3 extends TestCase {\n");
		expectedSource.append("    \n");
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

}
