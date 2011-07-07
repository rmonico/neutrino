package org.ita.neutrino.tests.addexplanation;

import static org.zero.utils.JUnitUtils.assertBlockEquals;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.TestBattery;
import org.ita.neutrino.abstracttestparser.TestParserException;
import org.ita.neutrino.addexplanation.AddExplanationRefactoring;
import org.ita.neutrino.codeparser.ParserException;
import org.ita.neutrino.junitgenericparser.JUnitGenericParser;
import org.ita.neutrino.tests.PluginAbstractTests;
import org.junit.Test;

public abstract class AddExplanationToAssertionTests extends PluginAbstractTests {
	
	protected ICompilationUnit refactoredCompilationUnit;
	protected JUnitGenericParser testParser;

	protected abstract void prepareTests() throws JavaModelException, ParserException, TestParserException;
	protected abstract StringBuilder getExpectedSource();
	
	@Test
	public void testAddExplanationToAssertionRefactoring() throws JavaModelException, RefactoringException, TestParserException, ParserException {

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
