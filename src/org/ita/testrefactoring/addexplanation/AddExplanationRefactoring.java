package org.ita.testrefactoring.addexplanation;

import org.ita.testrefactoring.abstracrefactoring.AbstractRefactoring;
import org.ita.testrefactoring.abstracrefactoring.InitialConditionNotMet;
import org.ita.testrefactoring.abstracrefactoring.RefactoringException;
import org.ita.testrefactoring.abstracttestparser.Assertion;
import org.ita.testrefactoring.codeparser.Environment;
import org.ita.testrefactoring.codeparser.Expression;
import org.ita.testrefactoring.codeparser.Type;

public class AddExplanationRefactoring extends AbstractRefactoring {

	public static class TargetIsNotJUnitAssertion extends InitialConditionNotMet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 60958052848660209L;
	
	}
	
	public static class TargetAlreadyHaveExplanation extends InitialConditionNotMet {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4078931382602215899L;
		
	}

	private String explanationString;
	private Assertion targetAssertion;
	
	@Override
	public InitialConditionNotMet checkInitialConditions() {
		if (!(getTargetFragment() instanceof Assertion)) {
			return new TargetIsNotJUnitAssertion();
		} else {
			targetAssertion = (Assertion) getTargetFragment();
		}
		
		if (targetAssertion.getExplanationIndex() != -1) {
			return new TargetAlreadyHaveExplanation();
		}
		
		return null;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
		Environment environment = getBattery().getCodeElement();
		
		Type javaLangStringType = environment.getTypeCache().get("java.lang.String"); 
		
		Expression explanationExpression = environment.getExpressionFactory().createLiteralExpression(javaLangStringType, explanationString);
		
		targetAssertion.getCodeElement().getParameterList().add(0, explanationExpression);
//		MethodParameter explanationParameter = new MethodParameter();
//		
//		LiteralExpression explanationStringExpression = new LiteralExpression();
//		
//		explanationStringExpression.setValue(explanationString);
//		
//		explanationParameter.setType("java.lang.String");
//		explanationParameter.setExpression(explanationStringExpression);
//		
//		targetAssertion.getParameters().add(0, explanationParameter);
	}

	public void setExplanationString(String explanationString) {
		this.explanationString = explanationString;
	}

}
