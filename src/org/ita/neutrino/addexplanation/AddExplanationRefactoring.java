package org.ita.neutrino.addexplanation;

import java.util.ArrayList;
import java.util.List;

import org.ita.neutrino.abstracrefactoring.AbstractRefactoring;
import org.ita.neutrino.abstracrefactoring.RefactoringException;
import org.ita.neutrino.abstracttestparser.Assertion;
import org.ita.neutrino.codeparser.Environment;
import org.ita.neutrino.codeparser.Expression;
import org.ita.neutrino.codeparser.Type;

public class AddExplanationRefactoring extends AbstractRefactoring {
	
	private String explanationString;
	private Assertion targetAssertion;
	
	@Override
	public List<String> checkInitialConditions() {
		List<String> problems = new ArrayList<String>();
		
		if ((!(getTargetFragment() instanceof Assertion)) || (getTargetFragment() == null)) {
			problems.add("Target is not a JUnit 4 valid assertion.");
		} else {
			targetAssertion = (Assertion) getTargetFragment();

			if (targetAssertion.getExplanation() != null) {
				problems.add("Target assertion still have a explanation.");
			}
		}
		
		
		return problems;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		Environment environment = getBattery().getCodeElement();
		
		Type javaLangStringType = environment.getTypeCache().get("java.lang.String"); 
		
		Expression explanationExpression = environment.getExpressionFactory().createLiteralExpression(javaLangStringType, explanationString);
		
		targetAssertion.getCodeElement().getParameterList().add(0, explanationExpression);
	}

	public void setExplanationString(String explanationString) {
		this.explanationString = explanationString;
	}

}
