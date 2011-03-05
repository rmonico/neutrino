package org.ita.testrefactoring.addexplanation;

import org.ita.testrefactoring.AbstractRefactoring;
import org.ita.testrefactoring.InitialConditionNotMet;
import org.ita.testrefactoring.RefactoringException;
import org.ita.testrefactoring.abstracttestparser.Assertion;

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

//	private String explanationString;
//	private JUnitAssertion targetAssertion;
	
	@Override
	public InitialConditionNotMet checkInitialConditions() {
		Assertion assertion;
		
		if (!(getTargetFragment() instanceof Assertion)) {
			return new TargetIsNotJUnitAssertion();
		} else {
			assertion = (Assertion) getTargetFragment();
		}
		
		if (hasExplanation(assertion)) {
			return new TargetAlreadyHaveExplanation();
		}
		
		return null;
	}

	private boolean hasExplanation(Assertion assertion) {
//		Testar se o primeiro parâmetro é java.lang.String
//		assertion.getMethodInvocation().resolveMethodBinding().getTypeParameters().
		
		return false;
	}

	@Override
	protected void doRefactor() throws RefactoringException {
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
//		this.explanationString = explanationString;
	}

}
